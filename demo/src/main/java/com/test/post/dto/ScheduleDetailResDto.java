package com.test.post.dto;

import com.test.post.Entity.ScheduleType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScheduleDetailResDto(

        LocalDateTime startDate,
        LocalDateTime endDate,
        ScheduleType scheduleType,
        String scheduleDetails
) {
}
