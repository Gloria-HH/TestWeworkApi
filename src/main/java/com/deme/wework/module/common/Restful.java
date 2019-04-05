package com.deme.wework.module.common;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Restful {

    public RequestSpecification requestSpecification = given();

    public Response send(String body, String url) {
        return given().log().all()
                .queryParam("access_token", Wework.getAccessToken())
                .body(body)
                .contentType("application/json; charset=UTF-8")
                .when().post(url)
                .then().log().all().statusCode(200).extract().response();
    }

    public String template(String path, Map<String,Object> map){
        DocumentContext documentContext = JsonPath.parse(this.getClass()
                .getResourceAsStream(path));
        map.entrySet().forEach(entry->{
            documentContext.set(entry.getKey(),entry.getValue());
        });
        return  documentContext.jsonString();
    }



}
