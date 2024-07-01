package com.sparta.lolcome.domain.follow.service;

import com.sparta.lolcome.domain.follow.repository.FollowRepository;
import com.sparta.lolcome.domain.follow.dto.FollowRequestDto;
import com.sparta.lolcome.domain.follow.dto.UnfollowRequestDto;
import com.sparta.lolcome.domain.follow.entity.Follow;
import com.sparta.lolcome.domain.post.dto.FollowedPostResponseDto;
import com.sparta.lolcome.domain.post.dto.PostResponseDto;
import com.sparta.lolcome.domain.post.entity.Post;
import com.sparta.lolcome.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    @Transactional
    public void follow(FollowRequestDto requestDto, User user) {

        if(followRepository.findByUserAndFollowUserId(user, requestDto.getFollowUserId()).isPresent()){
            throw new IllegalArgumentException("이미 팔로우 중인 계정입니다.");
        }
        else if(requestDto.getFollowUserId().equals(user.getUserId())){
            throw new IllegalArgumentException("본인에게 팔로우가 불가능합니다.");
        }

        Follow follow = new Follow(requestDto, user);

        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(UnfollowRequestDto requestDto, User user) {

        Follow follow = followRepository.findByUserAndFollowUserId(user, requestDto.getUnfollowUserId()).orElseThrow(
                ()-> new RuntimeException("팔로우 중인 계정이 아닙니다.")
        );

        followRepository.delete(follow);
    }

    public Object getFollowedPosts(User user, int page, int size) {
        List<Post> followedPosts = followRepository.findFollowedPostsByUserId(user.getUserId(),page,size);
        return followedPosts.stream()
                .map(FollowedPostResponseDto::new)
                .collect(Collectors.toList());
    }
}
