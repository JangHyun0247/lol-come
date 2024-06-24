package com.sparta.lolcome.domain.user.constant;

import lombok.Getter;

@Getter
public enum UserStatus {
    ADMIN,
    USER,
    UNVERIFIED,
    DELETED;
}
