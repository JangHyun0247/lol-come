package com.sparta.lolcome.domain.comment.dto;

import com.sparta.lolcome.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final Long post_id;
    private final String content;
    private final String name;

    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    //private final Long like_count;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getCommentId();
        this.post_id = comment.getPost().getPostId();
        this.content = comment.getContent();
        this.name = comment.getUser().getName();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        // this.like_count = comment.getLike_count();
    }
}
