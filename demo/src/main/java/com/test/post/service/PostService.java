package com.test.post.service;

import com.test.Member.detail.CustomUserDetails;
import com.test.Member.entity.DisabilityType;
import com.test.Member.entity.Member;
import com.test.Member.service.MemberService;
import com.test.auth.config.SecurityUtils;
import com.test.post.Entity.AssistanceType;
import com.test.post.Entity.Collage;
import com.test.post.Entity.Post;
import com.test.post.Entity.PostType;
import com.test.post.dto.*;
import com.test.post.exception.PostIdInvalidCheck;
import com.test.post.exception.PostNotFoundException;
import com.test.post.mapper.PostMapper;
import com.test.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Objects;

import static com.test.post.mapper.PostMapper.toEntity;

@RequiredArgsConstructor
@Service
public class PostService {

    private final MemberService memberService;
    private final PostRepository postRepository;


    //게시글 생성
    @Transactional
    public Long createPost(PostReqDto postReqDto) {
        //인증 객체 접근 && 로그인한 객체 가져오기
        Member member = SecurityUtils.getCurrentMember();
        Post post = toEntity(postReqDto, member);
        return postRepository.save(post).getId();
    }

    public PostEnumResDto getPostEnums() {
        return PostEnumResDto.builder()
                .assistanceTypes(List.of(AssistanceType.values()))
                .collages(List.of(Collage.values()))
                .disabilityTypes(List.of(DisabilityType.values()))
                .build();
    }


    // postId없으면  error 반환
    @Transactional(readOnly = true)
    public Post findPostIdOrExe(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> PostIdInvalidCheck.BUSSINESS_EXCEPTION);
    }

    // 존재하는 포스트인지 확인
    @Transactional(readOnly = true)
    public Post findPostByIdOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    // 해당 postType 조회
    public List<Post> getPostByType(PostType postType) {
        List<Post> posts = postRepository.findByPostType(postType);
        if (posts.isEmpty()) {
            throw new IllegalArgumentException("해당 유형의 게시글이 없습니다: " + postType);
        }
        return posts;
    }


    // 상세 게시물  게시글 조회
    public PostDetailDto findPost(Long postId, Long viewerId) {
        Post post = postRepository.findByIdWithAuthor(postId)
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);

        Boolean isLiked = false; // 좋아요 여부 로직 필요시 추가
        PostStatus postStatus = post.getPostStatus();
        // 상태 계산 필요시 변경

        return PostMapper.toPostDetailDto(post, isLiked, postStatus);
    }

    // 내가 작성한 게시글 목록 조회
    public PostCustomPage findPostsMyPage(int page, int size, String sort, PostType postType) {
        Member member = SecurityUtils.getCurrentMember();
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        Page<Post> posts = postRepository.findByAuthorIdAndPostType(member.getId(), postType, pageable);

        List<PostListItemDto> content = posts.stream()
                .map(post -> PostMapper.toPostListItemDto(post, false, PostStatus.MATCHING))
                .toList();

        return new PostCustomPage(content, posts.getTotalElements(), posts.isLast());
    }


    // 게시글 수정 내가 정보 가져오기
    @Transactional
    public Long updatePost(Long postId, PostUpdateReqDto postUpdateReqDto) {
        Post post = findPostByIdWithAuthorOrThrow(postId);
        Member author = SecurityUtils.getCurrentMember(); //  로그인한 사용자

        post.validateUpdateBy(author);
        post.updatePost(postUpdateReqDto);

        return post.getId();
    }

    @Transactional(readOnly = true)
    public Post findPostByIdWithAuthorOrThrow(Long postId) {
        return postRepository.findByIdWithAuthor(postId)
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }


}

