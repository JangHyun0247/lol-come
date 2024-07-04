package com.sparta.lolcome.domain.comment.service;

import com.sparta.lolcome.domain.comment.dto.CommentCreateRequestDto;
import com.sparta.lolcome.domain.comment.dto.CommentResponseDto;
import com.sparta.lolcome.domain.comment.entity.Comment;
import com.sparta.lolcome.domain.comment.repository.CommentRepository;
import com.sparta.lolcome.domain.post.dto.PostCreateRequestDto;
import com.sparta.lolcome.domain.post.entity.Post;
import com.sparta.lolcome.domain.post.repository.PostRepository;
import com.sparta.lolcome.domain.user.constant.UserMange;
import com.sparta.lolcome.domain.user.dto.SignupRequestDto;
import com.sparta.lolcome.domain.user.entity.User;
import net.bytebuddy.description.field.FieldList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    User user;
    Post post;;
    CommentCreateRequestDto commentCreateRequestDto;
    CommentCreateRequestDto commentCreateRequestDto1;
    Comment comment;
    Comment comment1;

    @BeforeEach
    void init(){
        SignupRequestDto signupRequestDto = new SignupRequestDto("Qwer1234","Qwer1234ok!","홍길동","한줄 소개", UserMange.USER);
        user = new User(signupRequestDto);
        PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto("게시물 작성");
        post = new Post(postCreateRequestDto);
        commentCreateRequestDto = new CommentCreateRequestDto("댓글 작성");
        comment = new Comment(commentCreateRequestDto, post, user);
        commentCreateRequestDto1 = new CommentCreateRequestDto("댓글 작성1");
        comment1 = new Comment(commentCreateRequestDto1, post, user);
    }

    @Test
    @DisplayName("댓글 작성 성공")
    void addComment() {
        //given
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        //when
        CommentResponseDto commentResponseDto = commentService.addComment(post.getPostId(), commentCreateRequestDto, user);

        //then
        assertEquals("댓글 작성",commentResponseDto.getContent());
    }

    @Test
    @DisplayName("댓글 작성 시 게시물 없을 때")
    void NotAddComment() {
        //given
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.empty());

        //when - then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.addComment(post.getPostId(),commentCreateRequestDto,user);
        });
        assertEquals("선택한 게시물이 존재하지 않습니다.",exception.getMessage());
    }

    @Test
    @DisplayName("댓글 단건 조회")
    void findCommentById() {
        //given
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));
        when(commentRepository.findById(comment.getCommentId())).thenReturn(Optional.of(comment));

        //when
        CommentResponseDto commentResponseDto = commentService.findCommentById(post.getPostId(), comment.getCommentId());

        //then
        assertEquals("댓글 작성",commentResponseDto.getContent());
    }

    @Test
    void findCommentAll() {
        //given
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));
        when(commentRepository.findAllByPostId(post.getPostId())).thenReturn(Arrays.asList(comment,comment1));

        //when
        List<CommentResponseDto> comments = commentService.findCommentAll(post.getPostId());

        //then
        assertEquals("댓글 작성",comments.get(0).getContent());
        assertEquals("댓글 작성1",comments.get(1).getContent());
        assertEquals(2,comments.size());
    }

    @Test
    void updateComment() {
    }

    @Test
    void deleteComment() {
    }
}