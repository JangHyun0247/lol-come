package com.sparta.lolcome.domain.auth.controller;

import com.sparta.lolcome.domain.auth.service.AuthService;
import com.sparta.lolcome.global.dto.HttpResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
//    private final UserService userService;
//    private final VerificationTokenRepository verificationTokenRepository;

    @PostMapping("/refresh")
    public ResponseEntity<HttpResponseDto> refresh(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        log.info("토큰 재발급 요청");
        Map<String, Object> tokenList = authService.tokenRegeneration(httpServletRequest, httpServletResponse);
        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .message("재발급 완료")
                        .data(tokenList)
                        .build()
        );
    }

//    @GetMapping("/confirm")
//    public ResponseEntity<HttpResponseDto> confirmToken(@RequestParam("token") String token) {
//        log.info("메일 인증");
//        return userService.confirmEmailToken(token);
//    }
}
