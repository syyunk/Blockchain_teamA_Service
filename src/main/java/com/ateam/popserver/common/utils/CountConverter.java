package com.ateam.popserver.common.utils;

import javax.persistence.AttributeConverter;

public class CountConverter implements AttributeConverter<Integer, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Integer attribute) {
        return attribute != null ? attribute : 1;
    }

    @Override
    public Integer convertToEntityAttribute(Integer dbData) {
        return dbData != null ? dbData : 1;
    }
}
