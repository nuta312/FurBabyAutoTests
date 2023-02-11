package com.furbaby.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

public class ObjectConverter {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converting json object string into the java object
     *
     * @param jsonObject
     * @param clazz java class that we want to deserialize
     * @return Java object
     * @throws JsonProcessingException
     */
    public static <T> T convertJsonObjectToJavaObject(String jsonObject, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(jsonObject, clazz);
    }

    /**
     * Converting json array string into the list of objects
     *
     * @param jsonArray
     * @param clazz array class that we want to deserialize
     * @return List of objects
     * @throws JsonProcessingException
     */
    public static <T> List<T> convertJsonArrayToListOfObjects(String jsonArray, Class<T[]> clazz) throws JsonProcessingException {
        return Arrays.asList(objectMapper.readValue(jsonArray, clazz));
    }
}
