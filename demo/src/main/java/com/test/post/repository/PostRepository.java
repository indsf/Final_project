package com.test.post.repository;


import com.test.Member.entity.DisabilityType;
import com.test.post.Entity.AssistanceType;
import com.test.post.Entity.Post;
import com.test.post.Entity.PostType;
import com.test.post.dto.PostCustomPage;
import com.test.post.dto.PostDetailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{

    // 게시글 종류별 조회
    List<Post> findByPostType(PostType postType);

    // 작성자(author)와 함께 단일 게시글 조회
    @Query("SELECT p FROM Post p JOIN FETCH p.author WHERE p.id = :postId")
    Optional<Post> findByIdWithAuthor(@Param("postId") Long postId);

    @Query(value = "SELECT post_status FROM post WHERE post_id = :postId", nativeQuery = true)
    String findPostStatusById(@Param("postId") Long postId);

    // 내가 쓴 글 조회 (Spring Data JPA 자동 생성)
    Page<Post> findByAuthorIdAndPostType(Long memberId, PostType postType, Pageable pageable);
}
