package com.sparta.lolcome.domain.comment.service;

import com.sparta.lolcome.domain.comment.dto.CommentCreateRequestDto;
import com.sparta.lolcome.domain.comment.dto.CommentResponseDto;
import com.sparta.lolcome.domain.comment.dto.CommentUpdateRequestDto;
import com.sparta.lolcome.domain.comment.entity.Comment;
import com.sparta.lolcome.domain.comment.repository.CommentRepository;
import com.sparta.lolcome.domain.post.entity.Post;
import com.sparta.lolcome.domain.post.repository.PostRepository;
import com.sparta.lolcome.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    @Transactional
    public CommentResponseDto addComment(Long postId, CommentCreateRequestDto requestDto, User user) {

        Post post = getExistingPost(postId);

        Comment comment = commentRepository.save(new Comment(requestDto, post, user));

        return new CommentResponseDto(comment, user);
    }

    public CommentResponseDto findCommentById(Long postId, Long commentId) {

        getExistingPost(postId);

        Comment comment = getExistingComment(commentId);

        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> findCommentAll(Long postId) {

        Post post = getExistingPost(postId);

        return commentRepository.findAllByPostId(post.getPostId()).stream().map(CommentResponseDto::new).toList();
    }

    @Transactional
    public void updateComment(Long commentId, CommentUpdateRequestDto requestDto, User user) {
        Comment comment = getExistingComment(commentId);
        if(currentUser(comment,user)) {
            comment.update(requestDto);
        }
        new CommentResponseDto(comment);
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = getExistingComment(commentId);
        if(currentUser(comment, user)) {
            commentRepository.delete(comment);
        }
    }

    private Post getExistingPost(Long postId){
       return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("선택한 게시물이 존재하지 않습니다."));
    }

    private Comment getExistingComment(Long commentId){
        return commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("선택한 댓글이 존재하지 않습니다."));
    }

    private Boolean currentUser(Comment comment, User user){
        if(!user.getUserId().equals(comment.getUser().getUserId())){
            throw new IllegalArgumentException("댓글 작성자만 수정할 수 있습니다.");
        }
        return true;
    }
}