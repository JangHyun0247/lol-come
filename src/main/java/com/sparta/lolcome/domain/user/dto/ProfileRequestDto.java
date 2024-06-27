package com.sparta.lolcome.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ProfileRequestDto {

    @NotNull(message = "이름은 필수입니다.")
    private String name;
    @NotNull(message = "자기소개는 필수입니다.")
    private String intro;
    @Setter
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "비밀번호는 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자 최소 하나를 포함해야 합니다.")
    private String password;

}
