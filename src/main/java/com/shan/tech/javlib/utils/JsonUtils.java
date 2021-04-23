package com.shan.tech.javlib.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

  private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static <T> T readFromJson(String jsonString, Class<T> clazz) throws JsonProcessingException {
    return OBJECT_MAPPER.readValue(jsonString, clazz);
  }

  public static String toJson(Object object) throws JsonProcessingException {
    return OBJECT_MAPPER.writeValueAsString(object);
  }
}
