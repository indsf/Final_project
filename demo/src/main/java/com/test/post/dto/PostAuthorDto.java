package com.test.post.dto;

import com.test.member.entity.DisabilityType;
import com.test.member.entity.Gender;
import lombok.Builder;

@Builder
public record PostAuthorDto(

        Long memberId,
        String nickname,
        String profileImageUrl,
        Integer age,
        Gender gender,
        DisabilityType disabilityType
) {
}
