package com.deme.wework.module.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeworkConfigTest {

    @Test
    void load() {
        WeworkConfig config=WeworkConfig.getInstance();
        System.out.println(config.getCorpId());
    }
}