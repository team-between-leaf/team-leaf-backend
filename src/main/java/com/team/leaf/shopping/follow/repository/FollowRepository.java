package com.team.leaf.shopping.follow.repository;

import com.team.leaf.shopping.follow.entity.Follow;
import com.team.leaf.user.account.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    long deleteByTargetUserAndSelfUser(AccountDetail targetUser, AccountDetail selfUser);

    Optional<Follow> findFollowByTargetUserAndSelfUser(AccountDetail targetUser, AccountDetail selfUser);

}
