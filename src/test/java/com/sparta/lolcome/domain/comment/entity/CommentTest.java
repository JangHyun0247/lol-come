package com.sparta.lolcome.domain.comment.entity;

import com.sparta.lolcome.domain.comment.dto.CommentCreateRequestDto;
import com.sparta.lolcome.domain.comment.dto.CommentUpdateRequestDto;
import com.sparta.lolcome.domain.post.dto.PostCreateRequestDto;
import com.sparta.lolcome.domain.post.entity.Post;
import com.sparta.lolcome.domain.user.constant.UserMange;
import com.sparta.lolcome.domain.user.dto.SignupRequestDto;
import com.sparta.lolcome.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    Comment comment;

    @BeforeEach
    void init(){
        PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto("게시물");
        Post post = new Post(postCreateRequestDto);

        SignupRequestDto signupRequestDto = new SignupRequestDto("wkdgus1111", "Qwer1234ok!","주장현","한줄 소개", UserMange.ADMIN);
        User user = new User(signupRequestDto);

        CommentCreateRequestDto commentCreateRequestDto = new CommentCreateRequestDto("댓글 내용 수정 전");
        comment = new Comment(commentCreateRequestDto,post, user);
    }

    @Test
    @DisplayName("댓글 업데이트")
    void update() {
        //given
        CommentUpdateRequestDto commentUpdateRequestDto = new CommentUpdateRequestDto("댓글 내용 수정 후");

        //when
        comment.update(commentUpdateRequestDto);

        //then
        assertEquals("댓글 내용 수정 후",comment.getContent());
    }
}