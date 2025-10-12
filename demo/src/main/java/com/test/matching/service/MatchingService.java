package com.test.matching.service;

import com.test.member.entity.Member;
import com.test.matching.entity.Matching;
import com.test.matching.entity.MatchingStatus;
import com.test.matching.repository.MatchingRepository;
import com.test.post.Entity.Post;
import com.test.comment.entity.Comment;
import com.test.post.repository.PostRepository;
import com.test.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 매칭 생성
    public Matching createMatching(Long postId, Long commentId, MatchingStatus status) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        System.out.println("✅ 게시글 조회 성공: " + post.getId() + " / 작성자ID=" + post.getAuthor().getId());

        // 댓글 조회
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isEmpty()) {
            System.out.println("❌ [오류] DB에서 commentId=" + commentId + " 인 댓글을 찾을 수 없음");
            System.out.println("📂 현재 댓글 총 개수: " + commentRepository.count());
            System.out.println("📋 댓글 목록 샘플: ");
            commentRepository.findAll().stream().limit(5)
                    .forEach(c -> System.out.println("  - ID=" + c.getId() + ", 내용=" + c.getContent()));
            throw new IllegalArgumentException("댓글 없음");
        }

        Comment comment = commentOpt.get();
        System.out.println("✅ 댓글 조회 성공: " + comment.getId() + " / 작성자ID=" + comment.getMember().getId());

        Member postOwner = post.getAuthor();
        Member commentWriter = comment.getMember();

        if (postOwner.getMemberRole() == commentWriter.getMemberRole()) {
            System.out.println("⚠️ [경고] 동일 역할 매칭 시도");
            throw new IllegalStateException("GIVER ↔ TAKER 간에만 매칭이 가능합니다.");
        }

        boolean exists = matchingRepository.existsByComment(comment);
        if (exists) {
            System.out.println("⚠️ [중복] 이미 매칭된 댓글");
            throw new IllegalStateException("이 댓글은 이미 매칭되었습니다.");
        }

        Matching matching = Matching.createMatching(post, comment, status);
        Matching saved = matchingRepository.save(matching);
        System.out.println("💾 매칭 생성 완료: ID=" + saved.getMatchingId());
        return saved;
    }

    public void cancelMatching(Long matchingId) {
        matchingRepository.deleteById(matchingId);
    }

    // ✅ 매칭 상태 변경 (완료 / 취소)
    public void updateMatchingStatus(Long matchingId, MatchingStatus newStatus) {
        Matching matching = matchingRepository.findById(matchingId)
                .orElseThrow(() -> new IllegalArgumentException("매칭 없음"));

        matching.setMatchingStatus(newStatus);
        matchingRepository.save(matching);
    }

    // 매칭 조회
    public Optional<Matching> getMatching(Long id) {
        return matchingRepository.findById(id);
    }

    // 매칭 삭제
    public void deleteMatching(Long id) {
        matchingRepository.deleteById(id);
    }
}
