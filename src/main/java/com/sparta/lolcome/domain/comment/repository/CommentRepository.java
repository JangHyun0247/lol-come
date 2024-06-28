package com.sparta.lolcome.domain.comment.repository;

import com.sparta.lolcome.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Optional 로 받으면 중복된 값에서 에러 발생 (중복된 댓글이 있을 수 있기 때문에 List 로 변경함)
    // PostId로 찾아오지 못하는 듯 해서 직접 쿼리 작성해서 조회하도록 함
    @Query("select c from Comment c where c.post.postId = :postId")
    List<Comment> findAllByPostId(@Param("postId") Long postId);
}