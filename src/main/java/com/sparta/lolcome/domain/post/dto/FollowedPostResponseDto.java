package com.sparta.lolcome.domain.post.dto;

import com.sparta.lolcome.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FollowedPostResponseDto {

    private Long postId;
    private String name;
    private String content;
    private Long likeCount;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    public FollowedPostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.name = post.getUser().getName();
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.created_at = post.getCreatedAt();
        this.modified_at = post.getModifiedAt();

    }

    }
