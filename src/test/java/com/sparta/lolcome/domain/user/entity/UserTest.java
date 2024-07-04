package com.sparta.lolcome.domain.user.entity;

import com.sparta.lolcome.domain.user.constant.UserMange;
import com.sparta.lolcome.domain.user.dto.ProfileRequestDto;
import com.sparta.lolcome.domain.user.dto.SignupRequestDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    private final SignupRequestDto signupRequestDto = new SignupRequestDto("wkdgus1111", "Qwer1234ok!","주장현","한줄 소개", UserMange.ADMIN);
    private final User user = new User(signupRequestDto);

    @Test
    void update() {
        //given
        ProfileRequestDto profileRequestDto = new ProfileRequestDto(
                "주장현","Qwer1234ok!!","다이아");

        //when
        user.update(profileRequestDto);

        //then
        assertEquals("주장현",user.getName());
        assertEquals("Qwer1234ok!!",user.getPassword());
        assertEquals("다이아",user.getIntro());
    }
}