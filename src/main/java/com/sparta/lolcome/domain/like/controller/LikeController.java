package com.sparta.lolcome.domain.like.controller;

import com.sparta.lolcome.domain.like.dto.LikeRequestDto;
import com.sparta.lolcome.domain.like.dto.LikeResponseDto;
import com.sparta.lolcome.domain.like.service.LikeService;
import com.sparta.lolcome.global.dto.HttpResponseDto;
import com.sparta.lolcome.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<HttpResponseDto> likeCreate(@RequestBody LikeRequestDto likeRequestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        likeService.likeCreate(likeRequestDto,userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .message("좋아요를 눌렀습니다.")
                        .build()
        );
    }

    @DeleteMapping
    public ResponseEntity<HttpResponseDto> likeCancel(@RequestBody LikeRequestDto likeRequestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        likeService.likeCancel(likeRequestDto,userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .message("좋아요가 취소되었습니다.")
                        .build()
        );
    }


    @GetMapping("/count")
    public ResponseEntity<HttpResponseDto> likeCount(@RequestBody LikeRequestDto likeRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .message("게시물이나 댓글의 좋아요가 조회되었습니다.")
                        .data(likeService.likeCount(likeRequestDto))
                        .build()
        );
    }

//    @GetMapping
//    public ResponseEntity<HttpResponseDto> MyLikedCount(@RequestBody LikeRequestDto likeRequestDto,
//                                                       @AuthenticationPrincipal UserDetailsImpl userDetails){
//
//        List<LikeResponseDto> likedContents = likeService.myLikedConunt(likeRequestDto, userDetails.getUser());
//
//        return ResponseEntity.status(HttpStatus.OK).body(
//                HttpResponseDto.builder()
//                        .message("게시물이나 댓글의 좋아요가 조회되었습니다.")
//                        .data(likedContents)
//                        .build()
//        );
//    }

}
