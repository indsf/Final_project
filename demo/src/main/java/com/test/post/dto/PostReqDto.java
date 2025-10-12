package com.test.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.common.validation.EnumTypeValue;
import com.test.post.Entity.AssistanceType;
import com.test.post.Entity.Collage;
import com.test.post.Entity.PostType;
import com.test.post.Entity.ScheduleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public record PostReqDto(
        @NotBlank(message = "제목을 입력해주세요")
        @Size(max = 30,message = "제목은 30자 이하로 작성해주세용")
        String title,

        @NotNull(message = "도움 유형을 선택하세용")
        @EnumTypeValue(enumClass = AssistanceType.class,message = "올바른 도움유형을 선택해주세용")
        String assistanceType,


        @Schema(description = "기간 시작", example = "2025-10-31T00:00:00", type = "string")
        @NotNull(message = "봉사 시작 날짜를 입력해주세용")
        LocalDateTime startDate,

        @Schema(description = "기간 종료일", example = "2025-10-31T00:00:00", type = "string")
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
        @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm:ss")
        LocalTime assistanceStartTime,

        @NotNull(message = "봉사 종료 시간을 입력해주세용")
        @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm:ss")
        LocalTime assistanceEndTime,

        @NotNull(message = "장애타입을 선택해주세요")
        String disabilityType,

        @NotNull(message = "작성자 ID는 필수입니다.")
        Long memberId
) {
    @AssertTrue(message = "시작 날짜는 현 시간이후 선택가능합니당")
    private boolean isStartDateValidCheck(){
        LocalDate startLocalDate = startDate.toLocalDate(); //날짜만 꺼내기
        LocalDate nowLocalDate = LocalDate.now(); // 현재 시간
        return !startLocalDate.isBefore(nowLocalDate); // 현재 날짜보다 전인지 check
    }

    @AssertTrue(message = "종료일은 시작일 이후여야 해용")
    private boolean isEndDateValidCheck() {
        LocalDate startLocalDate = startDate.toLocalDate();
        LocalDate endLocalDate = endDate.toLocalDate();
        return !endLocalDate.isBefore(startLocalDate);
    }

    @AssertTrue(message = "봉사 종료 시간은 시작 시간 보다 나중에 와야 합니다.")
    private boolean isAssistanceTimeValid() {
        return assistanceEndTime.isAfter(assistanceStartTime);
    }
}
