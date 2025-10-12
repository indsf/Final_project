package com.test.comment.controller;

import com.test.comment.dto.CommentCustomPage;
import com.test.comment.dto.CommentReqDto;
import com.test.comment.dto.CommentResponseDto;
import com.test.comment.service.CommentService;
import com.test.post.service.PostService;
import com.test.utils.api.ApiResponse;
import com.test.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/comments")
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;
    @Operation(summary = "댓글 생성", description = "댓글을 작성해주세요")
    @PostMapping("/{post-id}")
    public ApiResponse<ApiResponse.CustomBody<Long>> createComment(
            @PathVariable("post-id") Long postId,
            @Valid @RequestBody CommentReqDto dto
    ) {
        Long commentId = commentService.createComment(dto,postId);
        return ApiResponseGenerator.success(commentId,HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ApiResponse<?> getCommentsByPost(@PathVariable("postId") Long postId) {
        List<CommentResponseDto> comments = commentService.getCommentsByPost(postId);
        return ApiResponseGenerator.success(comments, HttpStatus.OK);
    }

}
