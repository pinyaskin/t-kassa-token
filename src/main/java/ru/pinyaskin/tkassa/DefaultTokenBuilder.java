package ru.pinyaskin.tkassa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;

/**
 * Default implementation of {@link TokenBuilder},
 * using for generating security token for TKassa by SHA256 algorithm
 *
 * @author pinyaskin iliya
 */
public class DefaultTokenBuilder implements TokenBuilder {
    private SortedMap<String, String> requestBody;
    private final ObjectMapper objectMapper;
    private String tokenFromRequest;

    DefaultTokenBuilder() {
        this.requestBody = new TreeMap<>();
        objectMapper = new ObjectMapper();
    }

    /**
     * Use method to convert JSON request from string to {@link TreeMap}.
     * {@link TreeMap} will be automatically sort it by alphabet.
     *
     * @param json request from TKassa
     * @return {@link TokenBuilder} to use next setter or build token
     */
    public TokenBuilder setRequestBody(String json) {
        try {
            LinkedHashMap<String, Object> object = objectMapper.readValue(json, LinkedHashMap.class);
            return setRequestBody(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Request cannot be converted");
        }
    }

    private TokenBuilder setRequestBody(LinkedHashMap<String, Object> request) {
        this.requestBody = convertRequestToMap(request);
        return this;
    }

    public TokenBuilder setPassword(String password) {
        requestBody.put("Password", password);
        return this;
    }

    public boolean isValid() {
        String token = generateToken();
        return Objects.equals(token, tokenFromRequest);
    }

    public String build() {
        return generateToken();
    }

    private SortedMap<String, String> convertRequestToMap(LinkedHashMap<String, Object> map) {
        SortedMap<String, String> result = new TreeMap<>();

        map.forEach((key, value) -> {
            if (Objects.equals(key, "Token")
                    && value.getClass().equals(String.class)) {
                this.tokenFromRequest = value.toString();
                return;
            }
            if (isNotNestedObject(value)) {
                result.put(key, value.toString());
            }
        });

        return result;
    }

    private boolean isNotNestedObject(Object obj) {
        return obj.getClass().isPrimitive()
                || obj.getClass().equals(String.class)
                || obj instanceof Number
                || obj instanceof Boolean;
    }

    private String generateToken() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry entry : requestBody.entrySet()) {
            builder.append(entry.getValue());
        }
        String result = builder.toString();

        return DigestUtils.sha256Hex(result);
    }
}