package com.test.post.mapper;

import com.test.member.entity.DisabilityType;
import com.test.member.entity.Member;
import com.test.post.Entity.*;
import com.test.post.dto.*;

public final class PostMapper {

    private PostMapper(){

    }

    public static Post toEntity(PostReqDto request, Member author) {
        return Post.builder()
                .author(author)
                .title(request.title())
                .assistanceType(AssistanceType.assFromValue(request.assistanceType()))
                .schedule(Schedule.builder()
                        .startDate(request.startDate())
                        .endDate(request.endDate())
                        .scheduleType(ScheduleType.schFromValue(request.scheduleType()))
                        .scheduleDetails(request.scheduleDetails())
                        .build())
                .collage(request.collage())
                .content(request.content())
                .postType(PostType.postFromValue(request.postType()))
                .disabilityType(DisabilityType.disFromValue(request.disabilityType()))
                .gender(author.getGender())
                .age(author.getAge())
                .assistanceTime(AssistanceTime.builder()
                        .AssistanceStartTime(request.assistanceStartTime())
                        .AssistanceEndTime(request.assistanceEndTime())
                        .build())
                .build();
    }



    // Response : 단일 게시글(게시글 상세) -> 이거 사용해야함
    public static PostDetailDto toPostDetailDto(Post post, Boolean isLiked, PostStatus postStatus) {
        return new PostDetailDto(
                toPostAuthorDto(post), //작성자 정보
                toPostDetailInfoDto(post, isLiked, postStatus)  //게시글 상세정보
        );
    }

    // Response : 게시글 리스트
    public static PostListItemDto toPostListItemDto(Post post, Boolean isLiked, PostStatus postStatus) {
        return new PostListItemDto(
                post.getId(),
                post.getTitle(),
                post.getCollage(),
                post.getPostType(),
                post.getDisabilityType(),
                toAssistanceResDto(post),
                postStatus,
                toScheduleListResDto(post),
                isLiked
        );
    }

    private static PostAuthorDto toPostAuthorDto(Post post) {
        return PostAuthorDto.builder()
                .memberId(post.getAuthor().getId())
                .nickname(post.getAuthor().getNickname())
                .profileImageUrl(post.getAuthor().getProfileImageUrl())
                .age(post.getAge())
                .gender(post.getGender())
                .disabilityType(post.getDisabilityType())
                .build();
    }

    private static PostDetailInfoDto toPostDetailInfoDto(Post post, Boolean isLiked, PostStatus postStatus) {
        return PostDetailInfoDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .schedule(toScheduleDetailResDto(post))
                .collage(post.getCollage())
                .content(post.getContent())
                .postType(post.getPostType())
                .createdAt(post.getCreatedAt())
                .assistance(toAssistanceResDto(post))
                .postStatus(postStatus)
                .isLiked(isLiked)
                .build();
    }

    private static AssistanceResDto toAssistanceResDto(Post post) {
        return new AssistanceResDto(
                post.getAssistanceType(),
                post.getAssistanceTime().getAssistanceStartTime(),
                post.getAssistanceTime().getAssistanceEndTime()
        );
    }

    private static ScheduleDetailResDto toScheduleDetailResDto(Post post) {
        return ScheduleDetailResDto.builder()
                .startDate(post.getSchedule().getStartDate())
                .endDate(post.getSchedule().getEndDate())
                .scheduleType(post.getSchedule().getScheduleType())
                .scheduleDetails(post.getSchedule().getScheduleDetails())
                .build();
    }

    private static ScheduleListResDto toScheduleListResDto(Post post) {
        return ScheduleListResDto.builder()
                .startDate(post.getSchedule().getStartDate())
                .endDate(post.getSchedule().getEndDate())
                .scheduleType(post.getSchedule().getScheduleType())
                .build();
    }


}
