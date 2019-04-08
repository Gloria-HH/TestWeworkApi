package com.deme.wework.module.web;

import com.deme.wework.module.common.API;
import com.deme.wework.module.common.Request;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;


public class HarTest {

    @Test
    public void test() {
        API api = new API();
        Response response = api.getResponseFromHar("/har/wework.har.json", new HashMap<>(), ".*tid=.*");
       response.then().statusCode(200);

    }
}
