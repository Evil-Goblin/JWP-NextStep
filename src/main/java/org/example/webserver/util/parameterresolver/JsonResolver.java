package org.example.webserver.util.parameterresolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Map;

public abstract class JsonResolver {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, String> parseJsonParam(String json) {
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            return Collections.emptyMap();
        }
    }
}
