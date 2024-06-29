package com.sparta.lolcome.domain.like.repository;

import com.sparta.lolcome.domain.comment.entity.Comment;
import com.sparta.lolcome.domain.post.entity.Post;

import java.util.List;

public interface LikeRepositoryCustom {
    List<Post> findLikedPostsByUserId(Long userId, int page, int size);

    List<Comment> findLikedCommentsByUserId(Long userId, int page, int size);

    Long countLikedPostsByUserId(Long userId);

    Long countLikedCommentsByUserId(Long userId);
}
