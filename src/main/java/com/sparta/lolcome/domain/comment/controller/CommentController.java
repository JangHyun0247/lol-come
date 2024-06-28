package com.sparta.lolcome.domain.comment.controller;

import com.sparta.lolcome.domain.comment.dto.CommentCreateRequestDto;
import com.sparta.lolcome.domain.comment.dto.CommentResponseDto;
import com.sparta.lolcome.domain.comment.dto.CommentUpdateRequestDto;
import com.sparta.lolcome.domain.comment.service.CommentService;
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
@RequestMapping("/api/post")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{post_id}/comment")
    public ResponseEntity<HttpResponseDto> addComment(@PathVariable Long post_id,
                                                      @RequestBody CommentCreateRequestDto requestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.addComment(post_id, requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .message("댓글이 작성이 완료되었습니다.")
                        .build()
        );
    }

    @GetMapping("/{post_id}/comment/{comment_id}")
    public ResponseEntity<HttpResponseDto> findCommentById(@PathVariable("post_id") Long post_id,
                                              @PathVariable("comment_id") Long comment_id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .message("게시물의 댓글 단건 조회가 완료되었습니다.")
                        .data(commentService.findCommentById(post_id, comment_id))
                        .build()
        );
    }

    @GetMapping("/{post_id}/comment/getList")
    public ResponseEntity<HttpResponseDto> findCommentAll(@PathVariable Long post_id) {
        List<CommentResponseDto> comments = commentService.findCommentAll(post_id);
        if (comments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    HttpResponseDto.builder()
                            .message("가장 먼저 댓글을 남겨보세요!")
                            .build()
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    HttpResponseDto.builder()
                            .message("게시물의 모든 댓글 조회 완료되었습니다.")
                            .data(comments)
                            .build()
            );
        }
    }

    @PatchMapping("/comment/{comment_id}")
    public ResponseEntity<HttpResponseDto> updateComment(
                                            @PathVariable("comment_id") Long comment_id,
                                            @RequestBody CommentUpdateRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.updateComment(comment_id, requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .message("댓글 수정이 완료되었습니다.")
                        .build()
        );
    }

    @DeleteMapping("/comment/{comment_id}")
    public ResponseEntity<HttpResponseDto> deleteComment(
                                                @PathVariable("comment_id") Long comment_id,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(comment_id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .message("댓글 삭제가 완료되었습니다.")
                        .build()
        );
    }
}
