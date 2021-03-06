package com.deme.wework.module.contact;

import com.deme.wework.utils.StringUtil;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.IsEqual.equalTo;

class DepartmentTest {

    Department department;

    @BeforeEach
    void setup() {
        if (department == null) {
            department = new Department();
//            department.deleteAll();
        }
    }


    @Test
    public void list() {
        Response response = department.list(new HashMap<>());

        response.then().statusCode(200).body("errcode", equalTo(0))
                .body("department.name[0]", equalTo("宇宙时空"));
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schema/contact/department.schema"));

    }



    @Test
    public void listWithParam() {
        String id = "1";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        department.list(map).then().statusCode(200)
                .body("errcode", equalTo(0))
                .body("department.name[0]", equalTo("宇宙时空"))
                .body("department.id[0]", equalTo(Integer.valueOf(id)));
    }

    @Test
    public void createByMap() {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("name", "Sale" + StringUtil.random());
            put("parentid", "1");
        }};

        department.create(map).then().statusCode(200).body("errcode", equalTo(0));
    }

    @Test
    public void delete() {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("name", "Sale" + StringUtil.random());
            put("parentid", "1");
        }};
        Object id = department.create(map).then().statusCode(200)
                .body("errcode", equalTo(0)).extract().path("id");
        department.delete(id.toString()).then().body("errcode", equalTo(0));
    }

    @Test
    public void update() {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("name", "Sale" + StringUtil.random());
            put("parentid", "1");
        }};
        Object id = department.create(map).then().statusCode(200).extract().path("id");
        map.put("name", id.toString() + "1");
        map.put("id", id);
        department.update(map).then().body("errcode", equalTo(0));
    }

}