package com.sparta.lolcome.global.util;


import com.sparta.lolcome.domain.user.constant.UserStatus;
import com.sparta.lolcome.domain.user.entity.User;
import com.sparta.lolcome.domain.user.repository.UserRepository;
import com.sparta.lolcome.global.error.exception.AuthorizationFailedException;
import com.sparta.lunchrecommender.domain.user.constant.UserStatus;
import com.sparta.lunchrecommender.domain.user.entity.User;
import com.sparta.lunchrecommender.domain.user.repository.UserRepository;
import com.sparta.lunchrecommender.global.error.exception.AuthorizationFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "JwtUtil")
@Component
@RequiredArgsConstructor
public class UserUtil {
    private final UserRepository userRepository;

    public User userVerifyById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("등록되지 않은 계정입니다")
        );
        if(user.getUserStatus().equals(UserStatus.DELETED)){
            throw new NullPointerException("삭제된 계정입니다");
        }
        return user;
    }

    public User userVerifyByLoginId(String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(
                () -> new AuthorizationFailedException("등록되지 않은 계정입니다")
        );
        if(UserStatus.UNVERIFIED.equals(user.getUserStatus())) {
            throw new AuthorizationFailedException("메일 인증이 필요합니다");
        }
        if(UserStatus.DELETED.equals(user.getUserStatus())){
            throw new AuthorizationFailedException("삭제된 계정입니다");
        }
        return user;
    }
}