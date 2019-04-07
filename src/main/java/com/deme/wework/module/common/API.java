package com.deme.wework.module.common;

import com.deme.wework.utils.StringUtil;
import com.deme.wework.utils.WeworkConstants;
import com.deme.wework.utils.YamlUtils;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;

public class API {

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
    public Response sendByJson(String body, String url, String method) {
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
     * 使用json转数组发送请求
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
        return sendByJson(body, url, method);
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

    /**
     * 使用yaml转数组发送请求
     *
     * @return
     */
    public Response getResponseFromYaml(String path, Map<String, Object> map) {
        reset();
        Restful restful = getRestfulFromYaml(path, map,"");
        return sendByRestful(restful);
    }

    /**
     * 使用yaml转数组发送请求
     *
     * @return
     */
    public Response getResponseFromYaml(String path, Map<String, Object> map, String dataJsonPath) {
        reset();
        Restful restful = getRestfulFromYaml(path, map, dataJsonPath);
        return sendByRestful(restful);
    }

    private Restful getRestfulFromYaml(String path, Map<String, Object> map, String dataJsonPath) {
        Restful restful = YamlUtils.getApiFromYaml(path, Restful.class);
        if (WeworkConstants.METHOD_GET.equals(restful.method)) {
            map.entrySet().forEach(entry -> {
                restful.query.replace(entry.getKey(),entry.getValue().toString());
            });
        }
        if (StringUtils.isNotBlank(dataJsonPath)) {
            restful.body = templateFromJson(dataJsonPath, map);
        }
        return restful;
    }

    private Response sendByRestful(Restful restful) {
        if (restful.headers != null && !restful.headers.isEmpty()) {
            restful.headers.entrySet().forEach(entry -> {
                requestSpecification.head(entry.getKey(), entry.getValue());
            });

        }
        if (restful.query != null) {
            restful.query.entrySet().forEach(entry -> {
                requestSpecification.queryParam(entry.getKey(), entry.getValue());
            });
        }
        if(StringUtils.isNotBlank(restful.body)){
            requestSpecification.body(restful.body);
        }

        return requestSpecification
                .contentType(WeworkConstants.CONTENT_TYPE_JSON)
                .when().request(restful.method, restful.url)
                .then().log().all().statusCode(200).extract().response();
    }


}
