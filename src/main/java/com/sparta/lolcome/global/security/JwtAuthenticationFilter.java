package com.sparta.lolcome.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.lolcome.domain.user.dto.LoginRequestDto;
import com.sparta.lolcome.global.dto.HttpResponseDto;
import com.sparta.lolcome.global.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login"); // 필터가 처리할 URL 설정
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("start JwtAuthenticationFilter");
        try {
            // HttpServletRequest 를 LoginRequestDto 객체로 변환
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            // 사용자 인증 정보 생성
            return getAuthenticationManager().authenticate(     // 사용자 인증
                    new UsernamePasswordAuthenticationToken(    // 사용자 인증정보 저장
                            requestDto.getLoginId(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        log.info("인증 성공");
        String loginId = ((UserDetailsImpl) authResult.getPrincipal()).getUsername(); // 인증된 사용자 ID 가져오기

        // JWT 생성 및 헤더 설정
        jwtUtil.generateTokenAndResponse(response, loginId);

        // 응답 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value()); // HTTP 상태 코드 설정

        // 성공 응답 메시지 작성 및 전송
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(new HttpResponseDto(HttpStatus.OK, "인증 성공"));
        response.getWriter().write(jsonResponse);
        log.info("로그인 완료");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.info("인증 실패");
        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 실패 응답 메시지 작성 및 전송
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(new HttpResponseDto(HttpStatus.UNAUTHORIZED, "인증 실패"));
        response.getWriter().write(jsonResponse);
    }
}