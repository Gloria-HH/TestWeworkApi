package com.deme.wework.module.contact;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Department extends Contact {

    public Response list(Map<String, Object> map) {
        return getResponseFromQueryParam(map, "https://qyapi.weixin.qq.com/cgi-bin/department/list");
    }


    public Response create(Map<String, Object> map) {
        return getResponseFromJson("/data/contact/department/create.json",
                map, "https://qyapi.weixin.qq.com/cgi-bin/department/create", "post");
    }

    public Response update(Map<String, Object> map) {
        return getResponseFromJson("/data/contact/department/update.json",
                map, "https://qyapi.weixin.qq.com/cgi-bin/department/update", "post");

    }

    public Response delete(final String id) {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("id", id);
        }};
        return delete(map);

    }

    public Response delete(Map<String, Object> map) {
        return getResponseFromQueryParam(map, "https://qyapi.weixin.qq.com/cgi-bin/department/list");
    }

    public void deleteAll() {
        List<Integer> ids = list(new HashMap<>()).body().path("department.id");
        System.out.println(ids);
        ids.forEach(id -> delete(id.toString()));
    }

}
