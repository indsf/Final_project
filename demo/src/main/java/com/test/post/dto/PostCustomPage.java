package com.test.post.dto;

import java.util.List;

public record PostCustomPage(
        List<PostListItemDto> content,
        Long totalPage, // 전체    게시물 개수
        Boolean last // 마지막 페이지
) {
}
