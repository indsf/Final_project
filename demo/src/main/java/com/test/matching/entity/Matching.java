package com.test.matching.entity;

import com.test.comment.entity.CommentEntity;
import com.test.post.Entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchingId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, unique = true)
    private Post post;

    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus;

    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false, unique = true)
    private CommentEntity comment; // 여기 추가

    public static Matching createMatching(Post post, CommentEntity comment, MatchingStatus status) {
        return Matching.builder()
                .post(post)
                .comment(comment)
                .matchingStatus(status)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
