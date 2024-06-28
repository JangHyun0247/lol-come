package com.sparta.lolcome.domain.like.entity;

import com.sparta.lolcome.domain.like.constant.LikeTypeEnum;
import com.sparta.lolcome.domain.like.dto.LikeRequestDto;
import com.sparta.lolcome.domain.user.entity.User;
import com.sparta.lolcome.global.util.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "liked")
public class Liked extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @Column
    @Enumerated(EnumType.STRING)
    private LikeTypeEnum likeTypeEnum;

    @Column
    private Long userId;

    @Column
    private Long contentId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    public Liked(LikeRequestDto requestDto){
        this.likeTypeEnum = requestDto.getLikeTypeEnum();
        this.contentId = requestDto.getContentId();
    }



}
