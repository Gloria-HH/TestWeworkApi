package com.deme.wework.module.contact;

import com.deme.wework.module.common.API;
import com.deme.wework.module.common.Wework;
import com.deme.wework.utils.WeworkConstants;

import static io.restassured.RestAssured.given;

public class Contact extends API {

    public Contact() {
    }

    @Override
    public void reset() {
        requestSpecification = given().log().all()
                .contentType(WeworkConstants.CONTENT_TYPE_JSON)
                .queryParam("access_token", Wework.getAccessToken())
                ;
        requestSpecification.filter( (req, res, ctx)->{
            //todo: 对请求 响应做封装
            return ctx.next(req, res);
        });
    }

}
