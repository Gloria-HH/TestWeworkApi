package com.deme.wework.module.common;

import com.deme.wework.utils.WeworkConstants;
import com.deme.wework.utils.YamlUtils;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class API {

    public RequestSpecification requestSpecification;

    public void reset() {
        requestSpecification = given();
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
        Request request = getRestfulFromParam(url, method, requestMap, jsonPath);
        return send(request);
    }

    private Request getRestfulFromParam(String url, String method, Map<String, Object> map, String dataJsonPath) {
        Request request = new Request(url, method);
        settingQueryParam(map, request);
        getBodyFromTemplate(map, dataJsonPath, request);
        return request;
    }

    private void settingQueryParam(Map<String, Object> map, Request request) {
        if (WeworkConstants.METHOD_GET.equals(request.getMethod())) {
            map.entrySet().forEach(entry -> {
                request.getQuery().replace(entry.getKey(), entry.getValue().toString());
            });
        }
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
        return getResponseFromYaml(path, map, "");
    }

    /**
     * 使用yaml转数组发送请求
     *
     * @param path         yaml路径
     * @param map          queryParam
     * @param dataJsonPath request body file
     * @return
     */
    public Response getResponseFromYaml(String path, Map<String, Object> map, String dataJsonPath) {
        reset();
        Request request = getRestfulFromYaml(path, map, dataJsonPath);
        return send(request);
    }

    private Request getRestfulFromYaml(String path, Map<String, Object> map, String dataJsonPath) {
        Request request = YamlUtils.getApiFromYaml(path, Request.class);
        settingQueryParam(map, request);
        getBodyFromTemplate(map, dataJsonPath, request);
        return request;
    }

    private void getBodyFromTemplate(Map<String, Object> map, String dataJsonPath, Request request) {
        if (StringUtils.isNotBlank(dataJsonPath)) {
            request.setBody(templateFromJson(dataJsonPath, map));
        }
    }

    /**
     * 发送请求
     *
     * @param request
     * @return
     */
    private Response send(Request request) {
        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            request.getHeaders().entrySet().forEach(entry -> {
                requestSpecification.head(entry.getKey(), entry.getValue());
            });

        }
        if (request.getBody() != null) {
            request.getQuery().entrySet().forEach(entry -> {
                requestSpecification.queryParam(entry.getKey(), entry.getValue());
            });
        }
        if (StringUtils.isNotBlank(request.getBody())) {
            requestSpecification.body(request.getBody());
        }

        return requestSpecification
                .contentType(WeworkConstants.CONTENT_TYPE_JSON)
                .when().request(request.getMethod(), request.getUrl())
                .then().log().all().statusCode(200).extract().response();
    }

    /**
     * 使用har转数组发送请求
     *
     * @param path    har路径
     * @param map     queryParam
     * @param pattern 匹配
     * @return
     */
    public Response getResponseFromHar(String path, Map<String, Object> map, String pattern) {
        reset();
        Request request = getRestfulFromHar(path, map, pattern);
        return send(request);
    }

    public Request getRestfulFromHar(String path, Map<String, Object> map, String pattern) {
        HarReader harReader = new HarReader();
        Har har = null;
        try {
            har = harReader.readFromFile(new File(getClass().getResource(path).getFile()));
        } catch (HarReaderException e) {
            e.printStackTrace();
        }
        if (har == null) {
            return null;
        }
        HarEntry harEntry = null;
        for (HarEntry entry : har.getLog().getEntries()) {
            if (entry.getRequest().getUrl().matches(pattern)) {
                harEntry = entry;
                break;
            }
        }
        if (harEntry == null) {
            throw new RuntimeException("harEntry is null");
        }
        //todo url的queryparam
        Request request = new Request(harEntry.getRequest().getUrl(), harEntry.getRequest().getMethod().name());
        request.setBody(harEntry.getRequest().getPostData().getText());
        return request;
    }


}
