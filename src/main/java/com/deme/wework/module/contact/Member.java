package com.deme.wework.module.contact;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;

import java.util.Map;

public class Member extends Contact {

    public Response list(String departmentId) {
        return requestSpecification.queryParam("department_id", departmentId)

                .when().get("https://qyapi.weixin.qq.com/cgi-bin/user/simplelist")

                .then().log().all().statusCode(200).extract().response()
                ;
    }
    public Response create(Map<String,Object> map) {
        reset();
        String body = template("/data/contact/member/create.json",map);
        return requestSpecification
                .body(body)
                .contentType("application/json; charset=UTF-8")
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/user/create")

                .then().log().all().statusCode(200).extract().response()
                ;
    }
}
