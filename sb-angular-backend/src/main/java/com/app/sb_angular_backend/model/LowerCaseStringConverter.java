package com.app.sb_angular_backend.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class LowerCaseStringConverter implements AttributeConverter<String, String> { //<entityType, DBColType>

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute != null) {
            return attribute.toLowerCase();
        }
        return null;
    }

    @Override
    public String convertToEntityAttribute(String dataFromDatabase) {
        return dataFromDatabase;
    }
}
