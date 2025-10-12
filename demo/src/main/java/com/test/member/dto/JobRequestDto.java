package com.test.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class JobRequestDto {
    private String region;
    private String disability;
    private int pay;
    private List<Map<String, Object>> recommendations;
}
