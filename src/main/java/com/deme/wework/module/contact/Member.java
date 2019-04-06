package com.deme.wework.module.contact;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;

import java.util.Map;

public class Member extends Contact {

    public Response list(Map<String, Object> map) {
        return getResponseFromQueryParam(map, "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist");

    }

    public Response create(Map<String, Object> map) {
        return getResponseFromJson("/data/contact/member/create.json",
                map, "https://qyapi.weixin.qq.com/cgi-bin/user/create", "post");

    }
}
