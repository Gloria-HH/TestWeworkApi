package com.deme.wework.module.contact;

import com.deme.wework.module.common.Restful;
import com.deme.wework.module.common.Wework;

import static io.restassured.RestAssured.given;

public class Contact extends Restful {

    public Contact() {
        reset();
    }

    public void reset() {
        requestSpecification = given().log().all()
                .queryParam("access_token", Wework.getAccessToken());
    }

}
