package com.deme.wework.module.contact;

import com.deme.wework.module.common.Wework;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

public class Department {

    public Response list(String id) {
        return given().log().all()
                .queryParam("access_token", Wework.getAccessToken())
                .queryParam("id", id)

                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/list")

                .then().log().all().statusCode(200).extract().response()
                ;
    }

    public Response create(String name, String parentId) {
        String body = JsonPath.parse(this.getClass().getResourceAsStream("/data/contact/createDepartment.json"))
                .set("$.name", name)
                .set("$.parentid", parentId)
                .jsonString();
        return given().log().all()
                .queryParam("access_token", Wework.getAccessToken())
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")

                .then().log().all().statusCode(200).extract().response()
                ;
    }

    public Response update(String name, String id) {
        String body = JsonPath.parse(this.getClass().getResourceAsStream("/data/contact/updateDepartment.json"))
                .set("$.name", name)
                .set("$.id", id).jsonString();
        return given().log().all()
                .queryParam("access_token", Wework.getAccessToken())
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/update")

                .then().log().all().statusCode(200).extract().response()
                ;
    }
    public Response delete(String id) {
        return given().log().all()
                .queryParam("access_token", Wework.getAccessToken())
                .queryParam("id",id)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")

                .then().log().all().statusCode(200).extract().response()
                ;
    }

}
