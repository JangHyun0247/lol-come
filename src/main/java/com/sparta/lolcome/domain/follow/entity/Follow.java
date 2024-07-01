package com.sparta.lolcome.domain.follow.entity;

import com.sparta.lolcome.domain.follow.dto.FollowRequestDto;
import com.sparta.lolcome.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "follow")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; //팔로우 한 사람

    @Column(name = "follow_user_id")
    private Long followUserId; //팔로우 된 사람

    public Follow(FollowRequestDto requestDto, User user){
        this.user = user;
        this.followUserId = requestDto.getFollowUserId();
    }

}
