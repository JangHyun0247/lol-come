package com.sparta.lolcome.domain.like.dto;

import com.sparta.lolcome.domain.like.constant.LikeTypeEnum;
import lombok.Getter;

@Getter
public class LikeRequestDto {
    private LikeTypeEnum likeTypeEnum;
    private Long contentId;
}
