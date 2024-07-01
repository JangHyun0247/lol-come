package com.sparta.lolcome.domain.follow.controller;

import com.sparta.lolcome.domain.follow.dto.FollowRequestDto;
import com.sparta.lolcome.domain.follow.dto.UnfollowRequestDto;
import com.sparta.lolcome.domain.follow.service.FollowService;
import com.sparta.lolcome.global.dto.HttpResponseDto;
import com.sparta.lolcome.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public ResponseEntity<HttpResponseDto> follow(@RequestBody FollowRequestDto requestDto,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails){
        followService.follow(requestDto,userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("팔로우가 되었습니다.")
                        .build()
        );
    }

    @DeleteMapping
    public ResponseEntity<HttpResponseDto> unfollow(@RequestBody UnfollowRequestDto requestDto,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails){
        followService.unfollow(requestDto,userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("팔로우가 취소 되었습니다.")
                        .build()
        );
    }

    @GetMapping("/postList")
    public ResponseEntity<HttpResponseDto> getFollowedPosts(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "5") int size){

        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("팔로우한 게시글 목록이 조회 되었습니다.")
                        .data(followService.getFollowedPosts(userDetails.getUser(),page,size))
                        .build()
        );
    }


}
