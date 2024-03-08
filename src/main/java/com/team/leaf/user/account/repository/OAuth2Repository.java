package com.team.leaf.user.account.repository;

import com.team.leaf.user.account.entity.OAuth2Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuth2Repository extends JpaRepository<OAuth2Account, Long> {

    Optional<OAuth2Account> findByEmail(String email);
    OAuth2Account findByPhone(String phone);
    boolean existsByNickname(String nickname);

}

