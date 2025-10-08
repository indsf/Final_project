package com.test.post.dto;

import com.test.Member.entity.DisabilityType;
import com.test.Member.entity.Gender;
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
