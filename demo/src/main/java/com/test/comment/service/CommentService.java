package com.test.comment.service;

import com.test.member.entity.Member;
import com.test.member.service.MemberService;
import com.test.auth.config.SecurityUtils;
import com.test.comment.dto.CommentReqDto;
import com.test.comment.dto.CommentResponseDto;
import com.test.comment.entity.Comment;
import com.test.comment.repository.CommentRepository;
import com.test.post.Entity.Post;
import com.test.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final MemberService memberService;
    private final PostService postService;
    private final CommentRepository commentRepository;



    // 댓글 생성
    @Transactional
    public Long createComment(CommentReqDto commentReqDto,Long postId) {
        Member currentMember = SecurityUtils.getCurrentMember();
        Post post = postService.findPostByIdOrThrow(postId);

        Comment comment = Comment.builder()
                .content(commentReqDto.content())
                .post(post)
                .member(currentMember)
                .build();
        return commentRepository.save(comment).getId();
    }

    private Comment commentToCreate(CommentReqDto commentReqDto, Post post, Member member) {
        return Comment.builder()
                .content(commentReqDto.content())
                .post(post)
                .member(member)
                .build();
    }

    public List<CommentResponseDto> getCommentsByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
                .map(CommentResponseDto::new)
                .toList();
    }


}
