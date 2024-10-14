package com.onestep.business_management.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class StringToMapConverter {
    public static Map<String, String> convertStringToMap(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> resultMap = new HashMap<>();
        try {
            // Chuyển đổi String JSON thành Map
            resultMap = objectMapper.readValue(jsonString, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultMap;
    }
}
