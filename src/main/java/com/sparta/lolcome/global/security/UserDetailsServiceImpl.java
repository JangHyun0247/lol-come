package com.sparta.lolcome.global.security;


import com.sparta.lolcome.domain.user.constant.UserStatus;
import com.sparta.lolcome.domain.user.entity.User;
import com.sparta.lolcome.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        // 사용자 객체 검색
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + loginId));
        //
        if(user.getUserStatus().equals(UserStatus.DELETED)){
            throw new UsernameNotFoundException("User Deleted" + loginId);
        }

        return new UserDetailsImpl(user);
    }
}