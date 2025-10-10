package com.test.post.dto;

import com.test.Member.entity.DisabilityType;
import com.test.post.Entity.AssistanceType;
import com.test.post.Entity.Collage;
import com.test.post.Entity.PostType;
import lombok.Builder;

import java.util.List;

@Builder
public record PostEnumResDto(
        List<AssistanceType> assistanceTypes,
        List<DisabilityType> disabilityTypes,
        List<Collage> collages
) {
}
