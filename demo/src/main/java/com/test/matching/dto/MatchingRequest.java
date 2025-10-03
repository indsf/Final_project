package com.test.matching.dto;

import com.test.matching.entity.MatchingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchingRequest {
    private Long postId;
    private Long commentId;
    private MatchingStatus matchingStatus;
}