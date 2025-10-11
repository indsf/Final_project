package com.test.post.service;

import com.test.post.Entity.Post;
import com.test.post.Entity.PostType;
import com.test.post.dto.AssistanceResDto;
import com.test.post.dto.PostListItemDto;
import com.test.post.dto.PostStatus;
import com.test.post.dto.ScheduleListResDto;
import com.test.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostListService {

    private final PostRepository postRepository;

    /**
     * 전체 게시글 조회
     */
    public List<PostListItemDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(this::toListDto)
                .toList();
    }

    /**
     * 게시글 타입별 조회 (TAKER, GIVER)
     */
    public List<PostListItemDto> getPostsByType(String type) {
        PostType postType = PostType.valueOf(type.toUpperCase());
        List<Post> posts = postRepository.findByPostType(postType);
        return posts.stream()
                .map(this::toListDto)
                .toList();
    }

    /**
     * Entity → DTO 변환
     * (postStatus는 DB 쿼리로 직접 가져옴)
     */
    private PostListItemDto toListDto(Post post) {
        // ✅ DB에서 post_status 컬럼 직접 조회
        String rawStatus = postRepository.findPostStatusById(post.getId());
        PostStatus status = null;
        if (rawStatus != null) {
            try {
                status = PostStatus.valueOf(rawStatus.toUpperCase());
            } catch (IllegalArgumentException ignored) {
                status = null;
            }
        }

        // ✅ schedule 변환
        ScheduleListResDto scheduleDto = null;
        if (post.getSchedule() != null) {
            scheduleDto = ScheduleListResDto.builder()
                    .startDate(post.getSchedule().getStartDate())
                    .endDate(post.getSchedule().getEndDate())
                    .scheduleType(post.getSchedule().getScheduleType())
                    .scheduleDetails(post.getSchedule().getScheduleDetails())
                    .build();
        }

        // ✅ assistanceTime 변환
        AssistanceResDto assistanceDto = null;
        if (post.getAssistanceTime() != null) {
            assistanceDto = AssistanceResDto.builder()
                    .assistanceStartTime(post.getAssistanceTime().getAssistanceStartTime())
                    .assistanceEndTime(post.getAssistanceTime().getAssistanceEndTime())
                    .build();
        }

        // ✅ 최종 DTO 빌드
        return PostListItemDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .collage(post.getCollage())
                .postType(post.getPostType())
                .disabilityType(post.getDisabilityType())
                .assistance(assistanceDto)
                .postStatus(status)
                .schedule(scheduleDto)
                .isLiked(false)
                .build();
    }
}

