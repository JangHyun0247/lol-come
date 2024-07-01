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
        //QueryDSL 사용을 위해 엔티티와 매핑되는 Q 클래스 생성
        QLiked liked = QLiked.liked;
        QPost post = QPost.post;

        //쿼리작성
        return queryFactory
                .select(post) //post 지정
                .from(liked) //liked 로부터 가져올건데
                .join(post).on(liked.contentId.eq(post.postId)) //liked 엔티티의 contentId 와 post 엔티티의 postId 매핑

                // liked 엔티티의 userId 와 받은 userId 가 같고 liked 의 Enum 타입이 POST 인 조건 지정
                .where(liked.userId.eq(userId).and(liked.likeTypeEnum.eq(LikeTypeEnum.POST)))
                //createdAt 내림차순으로 정렬
                .orderBy(post.createdAt.desc())
                // 페이지 시작 위치 지정
                .offset(page * size)
                //한 페이지의 최대 크기 지정
                .limit(size)
                //결과를 리스트로 반환
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

