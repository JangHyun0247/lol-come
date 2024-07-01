package com.sparta.lolcome.domain.follow.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowRequestDto {
    private Long followUserId;

    @JsonCreator
    public FollowRequestDto(Long followUserId){
        this.followUserId = followUserId;
    }
}
