package com.sparta.lolcome.domain.user.controller;

import com.sparta.lolcome.domain.user.dto.PasswordRequestDto;
import com.sparta.lolcome.domain.user.dto.SignupRequestDto;
import com.sparta.lolcome.domain.user.service.UserService;
import com.sparta.lolcome.global.dto.HttpResponseDto;
import com.sparta.lolcome.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<HttpResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        log.info("회원가입 요청");
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .message("회원가입 성공")
                        .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<HttpResponseDto> logout(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        log.info("로그아웃 요청");
        userService.logout(userDetailsImpl.getUser().getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .message("로그아웃이 완료 되었습니다.")
                        .build()
        );
    }

    @PatchMapping("/deleteAccount")
    public ResponseEntity<HttpResponseDto> deleteAccount(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody PasswordRequestDto passwordRequestDto
    ) {
        userService.deleteAccount(userDetailsImpl.getUser().getLoginId(), passwordRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .message("회원 탈퇴가 완료되었습니다.")
                        .build()
        );
    }
}
