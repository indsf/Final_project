package com.test.comment.dto;

import com.test.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long id;
    private String content;
    private String authorName;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.authorName = comment.getMember() != null
                ? comment.getMember().getNickname()
                : "익명";
    }
}