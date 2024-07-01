package com.sparta.lolcome.domain.follow.repository;

import com.sparta.lolcome.domain.post.entity.Post;

import java.util.List;

public interface FollowRepositoryCustom {
    List<Post> findFollowedPostsByUserId(Long userId, int page, int size);
}
