package com.sparta.lolcome.domain.user.dto;

import com.sparta.lolcome.domain.user.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String loginId;
    private String name;
    private String intro;

    public ProfileResponseDto(User user) {
        this.loginId = user.getLoginId();
        this.name = user.getName();
        this.intro = user.getIntro();
    }
}