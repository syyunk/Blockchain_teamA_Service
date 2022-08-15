package com.ateam.popserver.common.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CommonBooleanConverter implements AttributeConverter<Boolean, Boolean> {
    @Override
    public Boolean convertToDatabaseColumn(Boolean attribute) {
        return attribute != null && attribute;
    }

    @Override
    public Boolean convertToEntityAttribute(Boolean dbData) {
        return dbData != null && dbData;
    }
}
