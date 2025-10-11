package com.test.post.dto;

import java.util.List;

/**
 * 게시글 목록 응답 전용 DTO
 * ApiResponse 구조와 관계없이 단일 List 데이터만 감싸서 반환
 */
public record PostListResponse(List<PostListItemDto> posts) {}
