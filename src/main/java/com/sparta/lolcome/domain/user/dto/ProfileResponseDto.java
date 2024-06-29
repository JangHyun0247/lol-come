package com.sparta.lolcome.domain.user.dto;

import com.sparta.lolcome.domain.user.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String loginId;
    private String name;
    private String intro;
    private Long likedPostCount;
    private Long likedCommentCount;

    public ProfileResponseDto(User user) {
        this.loginId = user.getLoginId();
        this.name = user.getName();
        this.intro = user.getIntro();
    }

    public ProfileResponseDto(User user,Long likedPostCount, Long likedCommentCount) {
        this.loginId = user.getLoginId();
        this.name = user.getName();
        this.intro = user.getIntro();
        this.likedPostCount = likedPostCount;
        this.likedCommentCount = likedCommentCount;
    }
}