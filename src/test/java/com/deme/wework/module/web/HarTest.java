package com.deme.wework.module.web;

import com.deme.wework.module.common.API;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


public class HarTest {

    @Test
    @DisplayName("har file test")
    @Issue("120")
    @Link("http://www.baidu.com")
    @Description("read wework.har.json file, and send request")
    @Severity(SeverityLevel.NORMAL)
    public void test() {
        API api = new API();
        Response response = api.getResponseFromHar("/har/wework.har.json", new HashMap<>(), ".*tid=.*");
        response.then().statusCode(200);

    }


}
