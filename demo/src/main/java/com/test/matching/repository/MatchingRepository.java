package com.test.matching.repository;

import com.test.comment.entity.Comment;
import com.test.matching.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {
    boolean existsByComment(Comment comment);
}
