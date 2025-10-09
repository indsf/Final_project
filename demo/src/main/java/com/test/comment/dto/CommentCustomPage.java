package com.test.comment.dto;

import java.util.List;

public record CommentCustomPage(
        List<CommentResDto> content,
        Long cursor,
        Boolean nextPage
) {
}
