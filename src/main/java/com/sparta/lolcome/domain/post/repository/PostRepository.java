package com.sparta.lolcome.domain.post.repository;

import com.sparta.lolcome.domain.post.entity.Post;
import com.sparta.lolcome.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.createdAt BETWEEN :startDate AND :endDate")
    Page<Post> findAllByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

//    @Query("SELECT p FROM Post p JOIN FETCH p.likes l WHERE l.user.id = :userId")
//    List<Post> findPostsLikedByUser(@Param("userId") Long userId);

}