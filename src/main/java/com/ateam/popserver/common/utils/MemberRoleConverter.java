package com.ateam.popserver.common.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MemberRoleConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute != null ? attribute : "ROLE_USER";
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData != null ? dbData : "ROLE_USER";
    }
}
