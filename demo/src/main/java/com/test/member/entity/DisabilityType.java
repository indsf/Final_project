package com.test.member.entity;

import com.test.member.exception.DisabilityTypeValidCheck;
import com.test.common.validation.ValueEnum;

import java.util.Arrays;

public enum DisabilityType implements ValueEnum<String> {

    없음("없음"),
    지체장애("지체장애"),
    시각장애("시각장애"),
    청각장애("청각장애"),
    지적장애("지적장애"),
    정신장애("정신장애"),
    뇌병변장애("뇌병변장애"),
    신장장애("신장장애"),
    뇌전증장애("뇌전증장애"),
    자폐성장애("자폐성장애"),
    국가유공("국가유공"),
    언어장애("언어장애"),
    심장장애("심장장애"),
    호흡기장애("호흡기장애"),
    장루요루장애("장루요루장애"),
    간장애("간장애"),
    안면장애("안면장애"),
    기타("기타");

    private final String value;

    DisabilityType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static DisabilityType disFromValue(String value) {
        return Arrays.stream(DisabilityType.values())
                .filter(d -> d.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> DisabilityTypeValidCheck.bussinessException);
    }
}
