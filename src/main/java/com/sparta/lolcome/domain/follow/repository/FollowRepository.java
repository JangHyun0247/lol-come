package com.sparta.lolcome.domain.follow.repository;

import com.sparta.lolcome.domain.follow.entity.Follow;
import com.sparta.lolcome.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom {
    Optional<Follow> findByUserUserId(Long userId);

    Optional<Follow> findByUserAndFollowUserId(User user, Long followUserId);
}
