package com.deme.wework.module.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;

public class WeworkConfig {

    public String corpId;

    public String corpSecret ;

    public String contactSecret;

    private static WeworkConfig weworkConfig;

    public static WeworkConfig getInstance() {
        if (weworkConfig == null) {
            weworkConfig = load("/config/WeworkConfig.yaml");
        }

        return weworkConfig;
    }


    public static WeworkConfig load(String path) {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        WeworkConfig config;
        try {
            config = objectMapper.readValue(WeworkConfig.class.getResourceAsStream(path), WeworkConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return config;

       /* ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());
        try {
            System.out.println(objectMapper.writeValueAsString(WeworkConfig.getInstance()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*/
    }


}
