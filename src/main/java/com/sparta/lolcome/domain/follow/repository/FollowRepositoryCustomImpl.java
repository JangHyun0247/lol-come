package com.sparta.lolcome.domain.follow.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.lolcome.domain.follow.entity.QFollow;
import com.sparta.lolcome.domain.post.entity.Post;
import com.sparta.lolcome.domain.post.entity.QPost;
import com.sparta.lolcome.domain.user.entity.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryCustomImpl implements FollowRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> findFollowedPostsByUserId(Long userId, int page, int size){
        QFollow follow = QFollow.follow;
        QPost post = QPost.post;
//        QUser user = QUser.user;

        return queryFactory
                .select(post)
                .from(follow)
                .join(post).on(follow.followUserId.eq(post.user.userId))
                .where(follow.user.userId.eq(userId))
                .orderBy(post.user.name.asc(),post.createdAt.desc())
                .offset(page*size)
                .limit(size)
                .fetch();
    }
}
