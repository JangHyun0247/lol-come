package com.sparta.lolcome.domain.comment.dto;

import com.sparta.lolcome.domain.comment.entity.Comment;
import com.sparta.lolcome.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final Long post_id;
    private final String name;
    private final String content;
    private final Long likeCount;

    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getCommentId();
        this.post_id = comment.getPost().getPostId();
        this.name = comment.getUser().getName();
        this.content = comment.getContent();
        this.likeCount = comment.getLikeCount();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }

    public CommentResponseDto(Comment comment, User user) {
        this.id = comment.getCommentId();
        this.post_id = comment.getPost().getPostId();
        this.name = user.getName();
        this.content = comment.getContent();
        this.likeCount = comment.getLikeCount();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
