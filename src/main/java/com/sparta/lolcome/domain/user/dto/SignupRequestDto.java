package com.sparta.lolcome.domain.user.dto;

import com.sparta.lolcome.domain.user.constant.UserMange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotBlank(message = "loginId는 필수 항목입니다.")
    @Size(min = 4, max = 10, message = "아이디는 최소 4자 이상, 10자 이하여야 합니다.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "아이디는 알파벳 소문자(a~z)와 숫자(0~9)만 포함해야 합니다.")
    private String loginId;

    @NotBlank(message = "password는 필수 항목입니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자 이상, 15자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "비밀번호는 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자 최소 하나를 포함해야 합니다.")
    private String password;

    private String name;
    private String intro;
    private UserMange userMange;

    public SignupRequestDto(String loginId, String password, String name, String intro, UserMange userMange){
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.intro = intro;
        this.userMange = userMange;
    }
}
