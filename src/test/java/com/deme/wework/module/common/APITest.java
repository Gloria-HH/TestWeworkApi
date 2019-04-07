package com.deme.wework.module.common;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class APITest {

    @Test
    void getResponseFromYaml() {
        API api=new API();
        api.getResponseFromYaml("/api/department/list.yaml",new HashMap<>());
    }
}