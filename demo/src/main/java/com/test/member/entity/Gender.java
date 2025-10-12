package com.test.member.entity;

import com.test.common.validation.ValueEnum;

public enum Gender implements ValueEnum<String> {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String dbValue;

    Gender(String dbValue) {
        this.dbValue = dbValue;
    }

    @Override
    public String getValue() {
        return dbValue;
    }
}
