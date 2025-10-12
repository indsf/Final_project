package com.test.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverDto {
    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @NotBlank
    private String gender;

    private int age;

    @NotBlank
    private String memberRole;

    @NotBlank
    private String disabilityType;
}
