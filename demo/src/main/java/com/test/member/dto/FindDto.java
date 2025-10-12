package com.test.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindDto {
    @NotBlank(message = "값을 입력해주세요")
    private String value;
}