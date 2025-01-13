package com.cmc.dice.domain.space.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Converter
public class ImageUrlListConverter implements AttributeConverter<List<String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        try {
            // 리스트를 JSON 문자열로 변환
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting list to JSON: " + e.getMessage());
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try {
            // JSON 문자열을 리스트로 변환
            return objectMapper.readValue(dbData, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            // 예외 발생 시 빈 리스트 반환
            return new ArrayList<>();
        }
    }
}