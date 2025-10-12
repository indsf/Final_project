package com.test.post.controller;

import com.test.auth.resolver.MemberTokenId;
import com.test.common.annotation.AllowAnonymous;
import com.test.post.dto.PostDetailInfoDto;
import com.test.post.dto.PostListItemDto;
import com.test.post.dto.PostListResponse;
import com.test.post.service.PostListService;
import com.test.post.service.PostService;
import com.test.utils.api.ApiResponse;
import com.test.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "게시글 API", description = "게시글 조회 및 작성 관련 API")
public class PostListController {

    private final PostListService postListService;
    private final PostService postService;

    @Operation(summary = "모든 게시글 조회", description = "postType(TAKER/GIVER)에 상관없이 모든 게시글 목록을 반환")
    @GetMapping
    public ApiResponse<?> getAllPosts() {
        List<PostListItemDto> posts = postListService.getAllPosts();
        return ApiResponseGenerator.success(new PostListResponse(posts), HttpStatus.OK);
    }

    @Operation(summary = "게시글 종류별 조회", description = "postType으로 게시글을 필터링해 조회 (예: TAKER 또는 GIVER)")
    @GetMapping("/type/{postType}")
    public ApiResponse<?> getPostsByType(@PathVariable String postType) {
        List<PostListItemDto> posts = postListService.getPostsByType(postType);
        return ApiResponseGenerator.success(new PostListResponse(posts), HttpStatus.OK);
    }

    //@Operation(summary = "게시글 상세 조회", description = "PostDetailInfoDto 구조로 게시글 상세 정보를 조회합니다.")
//    @GetMapping("/info/{post-id}")
//    @AllowAnonymous
//    public ApiResponse<ApiResponse.CustomBody<PostDetailInfoDto>> getPostDetailInfo(
//            @PathVariable("postId") Long postId,
//            @MemberTokenId(required = false) Long memberId
//    ) {
//        PostDetailInfoDto dto = postService.getPostDetailInfo(postId, memberId);
//        return ApiResponseGenerator.success(dto, HttpStatus.OK);
//    }
//    @GetMapping("/info/{postid}")
//    @AllowAnonymous
//    public ApiResponse<ApiResponse.CustomBody<PostDetailInfoDto>> getPostDetailInfo(
//            @PathVariable("postid") Long postId,  // ✅ 이름 명시
//            @MemberTokenId(required = false) Long memberId
//    ) {
//        System.out.println("✅ Controller 진입: postId=" + postId + ", memberId=" + memberId);
//        PostDetailInfoDto dto = postService.getPostDetailInfo(postId, memberId);
//        return ApiResponseGenerator.success(dto, HttpStatus.OK);
//    }

}
