package com.test.member.dto;

import com.test.member.entity.DisabilityType;
import com.test.member.entity.Gender;
import com.test.member.entity.Role;

public record MemberResDto(
        Long memberId,
        String name,
        String nickName,
        String profileUrlImage,
        String email,
        Integer age,
        Gender gender,
        Role role,
        DisabilityType disabilityType
)
{

}
