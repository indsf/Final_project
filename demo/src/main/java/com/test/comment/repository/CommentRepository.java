package com.test.comment.repository;

import com.test.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Optional<Comment> findById(Long commentId);


    // 특정 ID를 가진 댓글을 찾기
    @Query("SELECT c From Comment c JOIN FETCH c.member WHERE c.id = :id")
    Optional<Comment> findByIdWithMember(@Param("id")Long commentId);
}
