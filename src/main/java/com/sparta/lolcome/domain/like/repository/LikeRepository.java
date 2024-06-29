package com.sparta.lolcome.domain.like.repository;

import com.sparta.lolcome.domain.like.constant.LikeTypeEnum;
import com.sparta.lolcome.domain.like.entity.Liked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Liked, Long>, LikeRepositoryCustom {
    Liked findByUserIdAndLikeTypeEnumAndContentId(Long userId, LikeTypeEnum likeTypeEnum, Long contentId);
}
