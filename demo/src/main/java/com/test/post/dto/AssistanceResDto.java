package com.test.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.post.Entity.AssistanceType;
import lombok.Builder;

import java.time.LocalTime;

// 응답 dto
@Builder
public record AssistanceResDto(
        AssistanceType assistanceType,
        @JsonFormat(pattern = "HH:mm") LocalTime assistanceStartTime,
        @JsonFormat(pattern = "HH:mm") LocalTime assistanceEndTime
) {
    public AssistanceResDto {
    }
}