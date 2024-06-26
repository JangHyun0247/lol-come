package com.sparta.lolcome.domain.post.entity;

import com.sparta.lolcome.domain.like.entity.Liked;
import com.sparta.lolcome.domain.post.dto.PostCreateRequestDto;
import com.sparta.lolcome.domain.post.dto.PostUpdateRequestDto;
import com.sparta.lolcome.domain.user.entity.User;
import com.sparta.lolcome.global.util.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Post")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;


    @Column(nullable = false)
    private String content;

    @Column
    private Long likeCount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Post(PostCreateRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.likeCount = 0L;
    }

    public void update(PostUpdateRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
