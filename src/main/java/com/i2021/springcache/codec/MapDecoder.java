package com.i2021.springcache.codec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapDecoder {

    private static final Logger log = LoggerFactory.getLogger("MapDecoder");

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Map<String, String> decodeMap(String value) {
        try {
            return mapper.readValue(value, new TypeReference<Map<String, String>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("MapDecoder-error: value: {}", value, e);
            return null;
        }
    }
}
