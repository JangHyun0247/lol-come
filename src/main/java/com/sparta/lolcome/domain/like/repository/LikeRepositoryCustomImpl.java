package com.sparta.lolcome.domain.like.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.lolcome.domain.comment.entity.Comment;
import com.sparta.lolcome.domain.comment.entity.QComment;
import com.sparta.lolcome.domain.like.constant.LikeTypeEnum;
import com.sparta.lolcome.domain.like.entity.QLiked;
import com.sparta.lolcome.domain.post.entity.Post;
import com.sparta.lolcome.domain.post.entity.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryCustomImpl implements LikeRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> findLikedPostsByUserId(Long userId, int page, int size) {
        QLiked liked = QLiked.liked;
        QPost post = QPost.post;

        return queryFactory
                .select(post)
                .from(liked)
                .join(post).on(liked.contentId.eq(post.postId))
                .where(liked.userId.eq(userId).and(liked.likeTypeEnum.eq(LikeTypeEnum.POST)))
                .orderBy(post.createdAt.desc())
                .offset(page * size)
                .limit(size)
                .fetch();
    }

    @Override
    public List<Comment> findLikedCommentsByUserId(Long userId, int page, int size) {
        QLiked liked = QLiked.liked;
        QComment comment = QComment.comment;

        return queryFactory
                .select(comment)
                .from(liked)
                .join(comment).on(liked.contentId.eq(comment.commentId))
                .where(liked.userId.eq(userId).and(liked.likeTypeEnum.eq(LikeTypeEnum.COMMENT)))
                .orderBy(comment.createdAt.desc())
                .offset(page * size)
                .limit(size)
                .fetch();
    }

    @Override
    public Long countLikedPostsByUserId(Long userId) {
        QLiked liked = QLiked.liked;

        return queryFactory
                .select(liked.count())
                .from(liked)
                .where(liked.userId.eq(userId).and(liked.likeTypeEnum.eq(LikeTypeEnum.POST)))
                .fetchOne();
    }

    @Override
    public Long countLikedCommentsByUserId(Long userId) {
        QLiked liked = QLiked.liked;

        return queryFactory
                .select(liked.count())
                .from(liked)
                .where(liked.userId.eq(userId).and(liked.likeTypeEnum.eq(LikeTypeEnum.COMMENT)))
                .fetchOne();
    }

}

