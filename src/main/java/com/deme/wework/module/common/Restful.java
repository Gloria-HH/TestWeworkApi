package com.deme.wework.module.common;

import com.deme.wework.utils.WeworkConstants;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Restful {

    public RequestSpecification requestSpecification;

    public void reset() {
        requestSpecification = given();
    }


    /**
     * 发送数据
     *
     * @param body
     * @param url
     * @param method
     * @return
     */
    public Response send(String body, String url, String method) {
        return requestSpecification
                .body(body)
                .contentType(WeworkConstants.CONTENT_TYPE_JSON)
                .when().request(method, url)
                .then().log().all().statusCode(200).extract().response();
    }


    /**
     * 从json组装请求数据
     *
     * @param path-json路径
     * @param map
     * @return
     */
    public String templateFromJson(String path, Map<String, Object> map) {

        DocumentContext documentContext = JsonPath.parse(this.getClass()
                .getResourceAsStream(path));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return documentContext.jsonString();
    }

    /**
     * 使用json组转数组发送请求
     *
     * @param jsonPath
     * @param requestMap
     * @param url
     * @param method
     * @return
     */
    public Response getResponseFromJson(String jsonPath, Map<String, Object> requestMap, String url, String method) {
        reset();
        String body = templateFromJson(jsonPath, requestMap);
        return send(body, url, method);
    }

    /**
     * 使用queryParam方式发送，只针对get请求
     *
     * @param requestMap
     * @param url
     * @return
     */
    public Response getResponseFromQueryParam(Map<String, Object> requestMap, String url) {
        reset();
        requestMap.entrySet().forEach(entry -> {
            requestSpecification.queryParam(entry.getKey(), entry.getValue());
        });
        return requestSpecification
                .when().get(url)
                .then().log().all().statusCode(200).extract().response()
                ;
    }


}
