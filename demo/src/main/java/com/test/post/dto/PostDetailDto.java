package com.test.post.dto;

import lombok.Builder;

@Builder// 최상위 DTO 조립 (작성자 + 게시글 상세)
public record PostDetailDto(
        PostAuthorDto author,
        PostDetailInfoDto post
) {
}
