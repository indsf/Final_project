package com.test.post.dto;

import com.test.member.entity.DisabilityType;
import com.test.post.Entity.Collage;
import com.test.post.Entity.PostType;
import lombok.Builder;

// 여러 게시글 보여주는데
@Builder
public record PostListItemDto(
        Long id,
        String title,
        Collage collage,
        PostType postType,
        DisabilityType disabilityType,
        AssistanceResDto assistance,
        PostStatus postStatus, // 매칭중 등 배지
        ScheduleListResDto schedule,
        Boolean isLiked  // 하트
) {

}