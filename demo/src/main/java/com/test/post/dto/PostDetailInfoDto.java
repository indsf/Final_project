package com.test.post.dto;

import com.test.Member.entity.DisabilityType;
import com.test.Member.entity.Gender;
import com.test.post.Entity.Collage;
import com.test.post.Entity.PostType;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record PostDetailInfoDto(
        Long id,
        String title,
        ScheduleDetailResDto schedule,
        Collage collage,
        String content,
        PostType postType,
        LocalDateTime createdAt,
        AssistanceResDto assistance,
        PostStatus postStatus,
        Boolean isLiked
) {
}
