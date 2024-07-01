package com.sparta.lolcome.domain.follow.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnfollowRequestDto {
    private Long unfollowUserId;

    @JsonCreator
    public UnfollowRequestDto(Long unfollowUserId){
        this.unfollowUserId = unfollowUserId;
    }
}
