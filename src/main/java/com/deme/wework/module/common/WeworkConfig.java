package com.deme.wework.module.common;

import com.deme.wework.utils.YamlUtils;

import java.util.Map;

public class WeworkConfig {

    private String corpId;

    private Map<String,String> secret;


    private static WeworkConfig weworkConfig;

    public static WeworkConfig getInstance() {
        if (weworkConfig == null) {
            weworkConfig = load("/config/WeworkConfig.yaml");
        }

        return weworkConfig;
    }


    public static WeworkConfig load(String path) {
        WeworkConfig config=YamlUtils.getApiFromYaml(path,WeworkConfig.class);
        return config;
    }

    public String getCorpId() {
        return corpId;
    }

    public Map<String, String> getSecret() {
        return secret;
    }

}
