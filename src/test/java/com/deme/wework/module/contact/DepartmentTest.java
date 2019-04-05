package com.deme.wework.module.contact;

import com.deme.wework.utils.StringUtil;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    Department department;

    @BeforeEach
    void setup() {
        if (department == null) {
            department = new Department();
        }
    }


    @Test
    public void list() {
        department.list("").then().statusCode(200)
                .body("errcode", equalTo(0))
                .body("department.name[0]", equalTo("宇宙时空"));
        String id = "1";
        department.list(id).then().statusCode(200)
                .body("errcode", equalTo(0))
                .body("department.name[0]", equalTo("宇宙时空"))
                .body("department.id[0]", equalTo(Integer.valueOf(id)));
    }

    @Test
    public void create() {
        department.create("Sale" + StringUtil.random(), "1").then().statusCode(200);
    }

    @Test
    public void createByMap() {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("name", "Sale" + StringUtil.random());
            put("parentid", "1");
        }};

        department.create(map).then().statusCode(200);
    }

    @Test
    public void delete() {
        Object id = department.create("Sale", "1").then().statusCode(200)
                .body("errcode", equalTo(0)).extract().path("id");
        department.delete(id.toString()).then().body("errcode", equalTo(0));
    }

    @Test
    public void update() {
        Object id = department.create("Sale", "1").then().statusCode(200).extract().path("id");
        department.update(id.toString() + "1", id.toString()).then().body("errcode", equalTo(0));
    }


}