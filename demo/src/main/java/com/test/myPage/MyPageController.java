package com.test.myPage;

import com.test.member.dto.MemberResDto;
import com.test.member.entity.Member;
import com.test.member.service.MemberService;
import com.test.post.Entity.PostType;
import com.test.post.dto.PostCustomPage;
import com.test.post.service.PostService;
import com.test.utils.api.ApiResponse;
import com.test.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // restapi 요청처리 가능
@RequiredArgsConstructor
@RequestMapping("/api/my-page")
public class MyPageController {

    private final MemberService memberService;
    private final PostService postService;





    // 세션 기반 내 프로필 조회
    @GetMapping("/me")
    public ApiResponse<ApiResponse.CustomBody<MemberResDto>> getMyProfile(
            @AuthenticationPrincipal(expression = "memberId") Long memberId
    ) {
        Member member = memberService.findMemberIdOrExe(memberId); // 혹은 repository.findById(...)
        return ApiResponseGenerator.success(new MemberResDto(member), HttpStatus.OK);
    }


    @Operation(summary = "내가 작성한 게시글 조회", description = "내가 작성한 게시글 목록을 조회합니다.")
    // 내가 작성한 글 목록
    @GetMapping("/my-posts")
    public ApiResponse<ApiResponse.CustomBody<PostCustomPage>> myPosts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "latest") String sort,
            @RequestParam(name = "postType", required = false) PostType postType
    ) {
        PostCustomPage body = postService.findPostsMyPage(page, size, sort, postType);
        return ApiResponseGenerator.success(body, HttpStatus.OK);
    }
}
