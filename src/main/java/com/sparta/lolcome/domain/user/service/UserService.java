package com.sparta.lolcome.domain.user.service;

import com.sparta.lolcome.domain.user.constant.UserStatus;
import com.sparta.lolcome.domain.user.dto.SignupRequestDto;
import com.sparta.lolcome.domain.user.entity.User;
import com.sparta.lolcome.domain.user.repository.UserRepository;
import com.sparta.lolcome.global.dto.HttpResponseDto;
import com.sparta.lolcome.global.util.UserUtil;
import com.sparta.lolcome.domain.user.dto.PasswordRequestDto;
import com.sparta.lolcome.global.dto.HttpResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //    private final JavaMailSender mailSender;
    //    private final VerificationTokenRepository verificationTokenRepository;

    public void signup(SignupRequestDto requestDto) {

        String password = passwordEncoder.encode(requestDto.getPassword());

        if(userRepository.findByLoginId(requestDto.getLoginId()).isPresent()){
            throw new IllegalArgumentException("이미 등록된 회원입니다.");
        }
        // 이메일, 아이디 중복검사 >
        User user = new User(requestDto);
        user.setUserStatus(UserStatus.UNAUTHORIZED);
        user.setPassword(password);

        registerUser(user);
    }

    // DB 값 중복 입력 시 오류 처리
    public void registerUser(User user) {
        try{
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            String errorMessage = Objects.requireNonNull(ex.getRootCause()).getMessage();
            if (errorMessage.contains("Duplicate entry")) {
                if (errorMessage.contains("login_id")) {
                    throw new IllegalArgumentException("ID already exists");
                }
            }
            throw new IllegalArgumentException("An unknown error occurred");
        }
    }

    @Transactional
    public void deleteAccount(String loginId, PasswordRequestDto passwordRequestDto) {
        log.info("deleteAccount");
        User user = userRepository.findByLoginId(loginId).orElseThrow();
        if(!passwordEncoder.matches(passwordRequestDto.getPassword(), user.getPassword())) {
            log.info("회원탈퇴 취소");
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. 회원탈퇴를 취소합니다.");
        }
        user.setStatus(UserStatus.DELETED);
    }

    @Transactional
    public void logout(Long userId) {
        User user = userUtil.userVerifyById(userId);
        user.setRefreshToken(null);
    }

//    public ResponseEntity<HttpResponseDto> confirmEmailToken(String token) {
//        EmailVerificationToken verificationToken = verificationTokenRepository.findByToken(token);
//        log.info(verificationToken.getUser().getUserId().toString() + " 사용자 메일 인증 시도");
//        // 유효하지 않은 토큰
//        if (verificationToken.getId() == null) {
//            log.info("유효하지 않은 키");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpResponseDto(HttpStatus.BAD_REQUEST, "유효하지 않은 키 입니다", null));
//        }
//        // 인증 기간 만료
//        if(verificationToken.getExpiresAt().isBefore(LocalDateTime.now())){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpResponseDto(HttpStatus.BAD_REQUEST, "시간이 만료되었습니다", null));
//        }
//        // 이미 인증된 사용자
//        User user = verificationToken.getUser();
//        if (UserStatus.ACTIVE.getStatus().equals(user.getStatus())) {
//            log.info("이미 인증된 계정");
//            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new HttpResponseDto(HttpStatus.ALREADY_REPORTED, "이미 인증된 계정입니다",null));
//        }
//        // 토큰 인증
//        user.setStatus(UserStatus.ACTIVE);
//        userRepository.save(user);
//        // 인증 완료
//        log.info("메일 정상 인증 완료");
//        return ResponseEntity.status(HttpStatus.OK).body(new HttpResponseDto(HttpStatus.OK, "인증이 완료되었습니다", null));
//    }

//    private void sendSimpleMessage(String to, String subject, String text) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        mailSender.send(message);
//
//    }
}