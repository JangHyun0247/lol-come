package com.sparta.lolcome.domain.like.service;

import com.sparta.lolcome.domain.comment.entity.Comment;
import com.sparta.lolcome.domain.comment.repository.CommentRepository;
import com.sparta.lolcome.domain.like.constant.LikeTypeEnum;
import com.sparta.lolcome.domain.like.dto.LikeRequestDto;
import com.sparta.lolcome.domain.like.dto.LikeResponseDto;
import com.sparta.lolcome.domain.like.entity.Liked;
import com.sparta.lolcome.domain.like.repository.LikeRepository;
import com.sparta.lolcome.domain.post.entity.Post;
import com.sparta.lolcome.domain.post.repository.PostRepository;
import com.sparta.lolcome.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    //좋아요
    @Transactional
    public void likeCreate(LikeRequestDto likeRequestDto, User user) {
        if (likeRequestDto.getLikeTypeEnum().equals(LikeTypeEnum.POST)) {
            handlePostLike(likeRequestDto, user);
        } else {
            handleCommentLike(likeRequestDto, user);
        }
    }

    //좋아요 취소
    @Transactional
    public void likeCancel(LikeRequestDto likeRequestDto, User user) {
        if (likeRequestDto.getLikeTypeEnum().equals(LikeTypeEnum.POST)) {
            handlePostLikeCancel(likeRequestDto, user);
        } else {
            handleCommentLikeCancel(likeRequestDto, user);
        }
    }

    //게시물 또는 댓글 좋아요 조회
    @Transactional
    public LikeResponseDto likeCount(LikeRequestDto likeRequestDto) {
        if (likeRequestDto.getLikeTypeEnum().equals(LikeTypeEnum.POST)) {
            Post post = postRepository.findById(likeRequestDto.getContentId()).orElseThrow(
                    () -> new NullPointerException("존재하지 않는 게시글입니다.")
            );
            return new LikeResponseDto(LikeTypeEnum.POST, post.getPostId(), post.getLikeCount());
        } else {
            Comment comment = commentRepository.findById(likeRequestDto.getContentId()).orElseThrow(
                    () -> new NullPointerException("존재하지 않는 댓글입니다.")
            );
            return new LikeResponseDto(LikeTypeEnum.COMMENT, comment.getCommentId(), comment.getLikeCount());
        }
    }

    //사용자가 좋아요 한 게시물 또는 댓글 좋아요 조회
//    public List<LikeResponseDto> myLikedConunt(LikeRequestDto likeRequestDto, User user) {
//        List<LikeResponseDto> likedContents = new ArrayList<>();
//
//        if (likeRequestDto.getLikeTypeEnum() == LikeTypeEnum.POST) {
//            List<Post> likedPosts = postRepository.findPostsLikedByUser(user.getUserId());
//            likedPosts.forEach(post -> {
//                likedContents.add(new LikeResponseDto(LikeTypeEnum.POST, post.getPostId(), post.getLikeCount()));
//            });
//        } else if (likeRequestDto.getLikeTypeEnum() == LikeTypeEnum.COMMENT) {
//            List<Comment> likedComments = commentRepository.findCommentsLikedByUser(user.getId());
//            likedComments.forEach(comment -> {
//                likedContents.add(new LikeResponseDto(LikeTypeEnum.COMMENT, comment.getCommentId(), comment.getLikeCount()));
//            });
//        }
//
//        return likedContents;
//    }

    //게시물 좋아요 메서드
    private void handlePostLike(LikeRequestDto likeRequestDto, User user) {
        Post post = postRepository.findById(likeRequestDto.getContentId()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")
        );
        if (likeRepository.findByUserIdAndLikeTypeEnumAndContentId(user.getUserId(), LikeTypeEnum.POST, likeRequestDto.getContentId()) != null) {
            throw new NullPointerException("좋아요를 이미 누른 게시물입니다.");
        }
        post.setLikeCount(post.getLikeCount() + 1);
        saveLike(likeRequestDto, user);
    }

    //댓글 좋아요 메서드
    private void handleCommentLike(LikeRequestDto likeRequestDto, User user) {
        Comment comment = commentRepository.findById(likeRequestDto.getContentId()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 댓글입니다.")
        );
        if (likeRepository.findByUserIdAndLikeTypeEnumAndContentId(user.getUserId(), LikeTypeEnum.COMMENT, likeRequestDto.getContentId()) != null) {
            throw new NullPointerException("이미 좋아요를 누르셨습니다.");
        }
        comment.setLikeCount(comment.getLikeCount() + 1);
        saveLike(likeRequestDto, user);
    }

    //게시물 좋아요 취소 메서드
    private void handlePostLikeCancel(LikeRequestDto likeRequestDto, User user) {
        Post post = postRepository.findById(likeRequestDto.getContentId()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")
        );
        Liked liked = getExistingLike(user, LikeTypeEnum.POST, likeRequestDto.getContentId());
        post.setLikeCount(post.getLikeCount() - 1);
        likeRepository.delete(liked);
    }

    //댓글 좋아요 취소 메서드
    private void handleCommentLikeCancel(LikeRequestDto likeRequestDto, User user) {
        Comment comment = commentRepository.findById(likeRequestDto.getContentId()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 댓글입니다.")
        );
        Liked liked = getExistingLike(user, LikeTypeEnum.COMMENT, likeRequestDto.getContentId());
        comment.setLikeCount(comment.getLikeCount() - 1);
        likeRepository.delete(liked);
    }

    //좋아요가 존재하는지
    private Liked getExistingLike(User user, LikeTypeEnum likeType, Long contentId) {
        Liked liked = likeRepository.findByUserIdAndLikeTypeEnumAndContentId(user.getUserId(), likeType, contentId);
        if (liked == null) {
            throw new NullPointerException("데이터가 존재하지 않습니다.");
        }
        if (!liked.getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("유저 정보가 올바르지 않습니다.");
        }
        return liked;
    }

    //좋아요 저장
    private void saveLike(LikeRequestDto likeRequestDto, User user) {
        Liked liked = new Liked(likeRequestDto);
        liked.setUserId(user.getUserId());
        likeRepository.save(liked);
    }


}
