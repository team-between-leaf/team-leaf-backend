package com.team.leaf.shopping.follow.service;

import com.team.leaf.shopping.follow.dto.FollowRequest;
import com.team.leaf.shopping.follow.entity.Follow;
import com.team.leaf.shopping.follow.repository.FollowRepository;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public void addFollow(AccountDetail accountDetail, FollowRequest request) {
        AccountDetail targetUser = accountRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("not found User"));

        Follow follow = Follow.createFollow(targetUser, accountDetail);
        if(followRepository.findFollowByTargetUserAndSelfUser(targetUser, accountDetail).isPresent()) {
            throw new RuntimeException("이미 팔로우 중인 사용자입니다.");
        }

        followRepository.save(follow);
    }

    @Transactional
    public void deleteFollow(AccountDetail accountDetail, FollowRequest request) {
        AccountDetail targetUser = accountRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("not found User"));

        followRepository.deleteByTargetUserAndSelfUser(targetUser, accountDetail);
    }

    public List<AccountDetail> getFollowingList(AccountDetail accountDetail) {
        return followRepository.findBySelfUser_AccountId(accountDetail.getUserId())
                .stream()
                .map(Follow::getTargetUser)
                .collect(Collectors.toList());
    }

    // 팔로워 목록 조회 메서드
    public List<AccountDetail> getFollowerList(AccountDetail accountDetail) {
        return followRepository.findByTargetUser_AccountId(accountDetail.getUserId())
                .stream()
                .map(Follow::getSelfUser)
                .collect(Collectors.toList());
    }
}
