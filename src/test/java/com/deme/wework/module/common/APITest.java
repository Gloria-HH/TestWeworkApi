package com.deme.wework.module.common;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class APITest {

    @Test
    void getResponseFromYaml() {
        API api = new API();
        Response response = api.getResponseFromYaml("/api/department/list.yaml", new HashMap<>());
        response.then().statusCode(200);
    }

    @Test
    void getRestfulFromHar() {
        API api = new API();
        Request request = api.getRestfulFromHar("/har/wework.har.json", new HashMap<>(), ".*tid=.*");
        assertNotNull(request.getUrl());
    }
}