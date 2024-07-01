package com.sparta.lolcome.domain.user.entity;

import com.sparta.lolcome.domain.follow.entity.Follow;
import com.sparta.lolcome.domain.user.constant.UserMange;
import com.sparta.lolcome.domain.user.constant.UserStatus;
import com.sparta.lolcome.domain.user.dto.ProfileRequestDto;
import com.sparta.lolcome.domain.user.dto.SignupRequestDto;
import com.sparta.lolcome.global.util.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "User")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(nullable = false, unique = true)
    private String loginId;
    @Setter
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String intro;
    @Column
    @Enumerated(EnumType.STRING)
    private UserMange userMange;
    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    @Setter
    @Column
    private String refreshToken;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime statusModifiedAt;

//    @OneToMany(mappedBy = "user")
//    private List<Follow> follows;

    public User(SignupRequestDto requestDto){
        this.loginId = requestDto.getLoginId();
        this.name = requestDto.getName();
        this.intro = requestDto.getIntro();
        this.userMange = requestDto.getUserMange();
        this.statusModifiedAt = LocalDateTime.now();
    }

    public void setStatus(UserStatus status) {
        if(!status.equals(this.userStatus)) {
            this.userStatus = status;
            this.statusModifiedAt = LocalDateTime.now();
        }
    }

    public void update(ProfileRequestDto requestDto) {
        this.name = requestDto.getName();
        this.intro = requestDto.getIntro();
        this.password = requestDto.getPassword();
    }
}
