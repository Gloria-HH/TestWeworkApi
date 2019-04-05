package com.deme.wework.module.contact;

import com.deme.wework.utils.StringUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @ValueSource(strings={"name_","test_","hello_"})
    public void create(final String prefix) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>() {{
            String random = StringUtil.random();
            String name=prefix+random;
            put("userid", name);
            put("name", name);
            put("alias", "alias" + random);
            put("department", Arrays.asList(1));
            put("is_leader_in_dept",Arrays.asList(1));
            put("email",random+"@sina.com");
            put("mobile","13"+random.substring(4));

        }};

        member.create(hashMap).then().statusCode(200)
                .body("errcode", equalTo(0));
    }

    @Test
    public void mobileTest(){
        String random = StringUtil.random();
        System.out.println(random.substring(4));
    }
}
