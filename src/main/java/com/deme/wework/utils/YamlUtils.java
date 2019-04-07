package com.deme.wework.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;

public class YamlUtils {
    public static <T> T getApiFromYaml(String path, Class<T> clazz) {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(YamlUtils.class.getResourceAsStream(path), clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
    }
}
