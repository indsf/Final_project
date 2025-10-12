package com.test.post.dto;

import com.test.member.entity.DisabilityType;
import com.test.post.Entity.AssistanceType;
import com.test.post.Entity.Collage;
import lombok.Builder;

import java.util.List;

@Builder
public record PostEnumResDto(
        List<AssistanceType> assistanceTypes,
        List<DisabilityType> disabilityTypes,
        List<Collage> collages
) {
}
