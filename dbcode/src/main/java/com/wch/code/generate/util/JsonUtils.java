package com.wch.code.generate.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author gaowenfeng
 * @date 2018/5/17
 */
public class JsonUtils {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T fromJSON(String jsonString, Class<T> clazz) throws Exception {

        T object = null;
        try {
            object = objectMapper.readValue(jsonString, clazz);
        } catch (Exception e) {
            throw e;
        }
        return object;
    }
}
