package com.sparta.lolcome.global.config;

import com.sparta.lolcome.domain.user.repository.UserRepository;
import com.sparta.lolcome.global.security.JwtAuthenticationFilter;
import com.sparta.lolcome.global.security.JwtAuthorizationFilter;
import com.sparta.lolcome.global.security.UserDetailsServiceImpl;
import com.sparta.lolcome.global.util.JwtUtil;
import com.sparta.lolcome.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserUtil userUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {  // 비밀번호 인코더로 BCryptPasswordEncoder
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();    // 인증 관리자 빈으로 받기
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil); // JWT 인증 필터를 생성합니다
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration)); // 인증 관리자를 설정
        return filter; // JWT 인증 필터를 반환
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userUtil, userDetailsService, userRepository); // JWT 인가 필터를 생성하고 반환
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정 비활성화
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // HTTP 요청에 대한 권한 설정
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/api/user/signup").permitAll() // 메인 페이지 요청 허가
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/login/**").permitAll()
                                .requestMatchers("/api/posts/getList").permitAll()
                                .requestMatchers("/api/post/{post_id}").permitAll()
                                .anyRequest().authenticated() // 그 외 모든 요청 인증처리
//                              .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
//                              .requestMatchers("/").permitAll() // 메인 페이지 요청 허가
        );

        // JWT 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class); // JWT 인가 필터를 JWT 인증 필터 앞에 추가
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가

        return http.build(); // SecurityFilterChain 을 빌드하여 반환
    }
}
