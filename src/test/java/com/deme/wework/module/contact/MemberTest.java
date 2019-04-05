package com.deme.wework.module.contact;

import com.deme.wework.utils.StringUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;

public class MemberTest {
    static Member member;

    @BeforeAll
    static void setup() {
        member = new Member();
    }

    @Test
    public void list() {
        String id = "1";
        member.list(id).then().statusCode(200)
                .body("errcode", equalTo(0));
    }

    @Test
    public void create() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>() {{
            String random = StringUtil.random();
            put("userid", "id" + random);
            put("name", "name" + random);
            put("alias", "alias" + random);
            put("department", Arrays.asList(1));
            put("is_leader_in_dept",Arrays.asList(1));
            put("email",random+"@sina.com");
            put("mobile","13"+random.substring(0,9));

        }};

        member.create(hashMap).then().statusCode(200)
                .body("errcode", equalTo(0));
    }
}
