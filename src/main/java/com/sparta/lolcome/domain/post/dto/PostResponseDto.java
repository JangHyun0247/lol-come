package com.sparta.lolcome.domain.post.dto;

import com.sparta.lolcome.domain.post.entity.Post;
import com.sparta.lolcome.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private Long postId;
    private String name;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    public PostResponseDto(Post post, User user) {
        this.postId = post.getId();
        this.name = user.getName();
        this.content = post.getContent();
        this.created_at = post.getCreatedAt();
        this.modified_at = post.getModifiedAt();

    }

    }
