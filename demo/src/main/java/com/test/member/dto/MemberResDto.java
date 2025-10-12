package com.test.member.dto;

import com.test.member.entity.DisabilityType;
import com.test.member.entity.Gender;
import com.test.member.entity.Member;
import com.test.member.entity.Role;
import lombok.Builder;

@Builder
public record MemberResDto(
        Long id,
        String name,
        String nickname,
        String profileImageUrl,
        String email,
        Integer age,
        Gender gender,
        DisabilityType disabilityType
)
{
    public MemberResDto(Member member) {
        this(
                member.getId(),
                member.getName(),
                member.getNickname(),
                member.getProfileImageUrl(),
                member.getEmail(),
                member.getAge(),
                member.getGender(),
                member.getDisabilityType()
        );
    }
}
