package com.test.post.Entity;

import com.test.common.validation.ValueEnum;
import com.test.post.exception.AssistanceTypeInvalidCheck;
import lombok.Getter;

import java.util.Arrays;
/// 도움유형
@Getter
public enum AssistanceType implements ValueEnum<String> {
    STUDY("STUDY"),
    MEAL("MEAL"),
    MOVE("MOVE"),
    ETC("ETC");

    private String value;

    AssistanceType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static AssistanceType assFromValue(String value){
        return Arrays.stream(AssistanceType.values())
                .filter(ft -> ft.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> AssistanceTypeInvalidCheck.BUSSINESS_EXCEPTION);
    }

}
