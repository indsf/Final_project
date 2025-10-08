package com.test.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.common.validation.EnumTypeValue;
import com.test.post.Entity.AssistanceType;
import com.test.post.Entity.Collage;
import com.test.post.Entity.PostType;
import com.test.post.Entity.ScheduleType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record PostUpdateReqDto(
        @NotBlank(message = "제목을 입력해주세요")
        @Size(max = 30,message = "제목은 30자 이하로 작성해주세용")
        String title,

        @NotNull(message = "도움 유형을 선택하세용")
        @EnumTypeValue(enumClass = AssistanceType.class,message = "올바른 도움유형을 선택해주세용")
        String assistanceType,

        @NotNull(message = "봉사 시작 날짜를 입력해주세용")
        LocalDateTime startDate,

        @NotNull(message = "봉사 종료 날짜를 입력해주세용")
        LocalDateTime endDate,

        @NotNull(message = "정지적 / 주기적 선택해주세요(일정)")
        @EnumTypeValue(enumClass = ScheduleType.class , message = "올바른 스케줄타입을 선택해주세용")
        String scheduleType,

        @NotBlank(message = "일정을 상세히 적어주세용")
        String scheduleDetails,

        @NotNull(message = "현재 있는 위치의 대학의 위치를 적어주세용 ex)사범대,공대,경영대")
        Collage collage,

        @NotBlank(message = "도움이 필요한 상세 내용을 적어주세요")
        @Size(max = 500, message = "상세 내용은 500자 이내로 작성해주세용")
        String content,

        @NotNull(message = "게시글의 종류를 선택해주세요")
        @EnumTypeValue(enumClass = PostType.class,message = "게시글의 종류를 다시 선택해주세용")
        String postType,

        @NotNull(message = "봉사 시작 시간을 입력해주세용")
        @JsonFormat(pattern = "HH:mm:ss")
        LocalTime assistanceStartTime,

        @NotNull(message = "봉사 종료 시간을 입력해주세용")
        @JsonFormat(pattern = "HH:mm:ss")
        LocalTime assistanceEndTime
)
{

    // 1) 시작은 현재 시각 이후
    @AssertTrue(message = "시작 일시(startDate)는 현재 시각 이후여야 합니다.")
    public boolean isStartDateValidCheck() {
        return startDate.isAfter(LocalDateTime.now());
    }

    // 2) 종료일은 시작일과 같거나 이후 (날짜만 비교 유지 시)
    @AssertTrue(message = "종료일은 시작일과 같거나 이후여야 해용")
    public boolean isEndDateValidCheck() {
        return !endDate.toLocalDate().isBefore(startDate.toLocalDate());
    }

    // 3) 종료시간은 시작시간 이후 (같은 날 기준) — 자정 넘김을 허용하지 않는 정책
    @AssertTrue(message = "봉사 종료 시간은 시작 시간보다 나중이어야 합니다.")
    public boolean isAssistanceTimeValid() {
        // 같은 날이라면 endTime > startTime 여야 함
        if (endDate.toLocalDate().isEqual(startDate.toLocalDate())) {
            return assistanceEndTime.isAfter(assistanceStartTime);
        }
        // 다른 날이면 (다음 날 이상) 시간 역전이어도 OK (자정 넘김 허용)
        return true;
    }

    // 4) 전체 시각 비교(선택): startDate+startTime < endDate+endTime 여야 한다
    @AssertTrue(message = "종료 일시는 시작 일시보다 나중이어야 합니다.")
    public boolean isTimelineOrderValid() {
        LocalDateTime startDateTime = startDate.with(assistanceStartTime);
        LocalDateTime endDateTime = endDate.with(assistanceEndTime);
        return endDateTime.isAfter(startDateTime);
    }
}

