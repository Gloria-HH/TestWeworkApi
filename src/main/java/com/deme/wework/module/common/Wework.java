package com.deme.wework.module.common;

import com.deme.wework.utils.WeworkConstants;
import org.apache.commons.lang3.StringUtils;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Wework {

    private static String ACCESS_TOKEN;

    static String getWeworkAccessToken(String secret) {
        return given()
                .queryParam("corpid", WeworkConstants.CORP_ID)
                .queryParam("corpsecret", secret)

                .when().get(" https://qyapi.weixin.qq.com/cgi-bin/gettoken")

                .then().log().all()
                .statusCode(200)
                .body("errcode", equalTo(0)).extract().path("access_token")
                ;

    }

    public static String getAccessToken() {
        if (StringUtils.isBlank(ACCESS_TOKEN)) {
            ACCESS_TOKEN = getWeworkAccessToken(WeworkConstants.CONTACT_SECRET);
        }
        return ACCESS_TOKEN;
    }


}
