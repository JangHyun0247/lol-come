package com.sparta.lolcome.domain.post.entity;

import com.sparta.lolcome.domain.post.dto.PostCreateRequestDto;
import com.sparta.lolcome.domain.post.dto.PostUpdateRequestDto;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    Post post;

    @BeforeEach
    void init(){
        PostCreateRequestDto createRequestDto = new PostCreateRequestDto("게시물 내용 수정 전");
        post = new Post(createRequestDto);

    }

    @Test
    @DisplayName("게시물 업데이트")
    void update() {
        //given
        PostUpdateRequestDto updateRequestDto = new PostUpdateRequestDto("게시물 내용 수정 후");

        //when
        post.update(updateRequestDto);

        //then
        assertEquals("게시물 내용 수정 후",post.getContent());
    }
}