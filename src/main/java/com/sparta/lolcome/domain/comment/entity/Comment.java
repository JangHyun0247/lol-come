package com.sparta.lolcome.domain.comment.entity;

import com.sparta.lolcome.domain.comment.dto.CommentCreateRequestDto;
import com.sparta.lolcome.domain.comment.dto.CommentUpdateRequestDto;
import com.sparta.lolcome.domain.post.entity.Post;
import com.sparta.lolcome.domain.user.entity.User;
import com.sparta.lolcome.global.util.Timestamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Table(name = "Comment")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @NotBlank(message = "내용을 입력해주세요.")
    @Column(nullable = false)
    private String content;

    @Column
    private Long likeCount;

    @ManyToOne
    @JoinColumn(name="post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Comment(CommentCreateRequestDto requestDto, Post post, User user) {
        this.content = requestDto.getContent();
        this.likeCount = 0L;
        this.post = post;
        this.user = user;
    }

    public void update(CommentUpdateRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
