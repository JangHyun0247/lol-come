package com.sparta.lolcome.domain.like.dto;

import com.sparta.lolcome.domain.like.constant.LikeTypeEnum;

public class LikeResponseDto {
    public LikeTypeEnum likeTypeEnum;
    public Long contentId;
    public Long likeCount;


    public LikeResponseDto(LikeTypeEnum likeTypeEnum, Long contentId, Long likeCount) {
        this.likeTypeEnum = likeTypeEnum;
        this.contentId = contentId;
        this.likeCount = likeCount;
    }
}
