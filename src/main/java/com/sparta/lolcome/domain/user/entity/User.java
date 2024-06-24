package com.sparta.lolcome.domain.user.entity;

import com.sparta.lolcome.domain.user.constant.UserStatus;
import com.sparta.lolcome.domain.user.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false, unique = true)
    private String loginId;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String intro;
    @Column
    private UserStatus userStatus;
    @Setter
    @Column
    private String refreshToken;

    public User(SignupRequestDto requestDto){
        this.loginId = requestDto.getLoginId();
        this.password = requestDto.getPassword();
        this.name = requestDto.getName();
        this.intro = requestDto.getIntro();
        this.userStatus = requestDto.getUserStatus();
    }

}
