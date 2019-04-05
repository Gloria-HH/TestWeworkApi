package com.deme.wework.module.common;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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

}
