package com.test.matching.service;

import com.test.Member.entity.Member;
import com.test.Member.entity.MemberRole;
import com.test.matching.entity.Matching;
import com.test.matching.entity.MatchingStatus;
import com.test.matching.repository.MatchingRepository;
import com.test.post.Entity.Post;
import com.test.comment.entity.CommentEntity;
import com.test.post.Entity.PostType;
import com.test.post.repository.PostRepository;
import com.test.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 매칭 생성
    public void createMatching(Long postId, Long commentId, MatchingStatus status) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));


        Member postMemberId = post.getAuthor();       // 게시글 작성자
        Member commentMemberId = comment.getMember();  // 댓글 작성자

        if (postMemberId.getMemberRole() == commentMemberId.getMemberRole()) {
            throw new IllegalArgumentException("동일한 역할끼리는 매칭할 수 없습니다.");
        }


        Matching matching = Matching.createMatching(post, comment, status);
        matchingRepository.save(matching);
    }

    // 매칭 조회
    public Optional<Matching> getMatching(Long matchingId) {
        return matchingRepository.findById(matchingId);
    }

    // 매칭 취소
    public void deleteMatching(Long matchingId) {
        matchingRepository.deleteById(matchingId);
    }

    //채팅룸 -> 역할확인 -> 웹소켓 -> 채팅해
}
