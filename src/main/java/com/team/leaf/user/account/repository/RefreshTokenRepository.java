package com.team.leaf.user.account.repository;

import com.team.leaf.user.account.dto.request.jwt.Platform;
import com.team.leaf.user.account.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Ref;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUserEmail (String userEmail);

    List<RefreshToken> findByUserEmailAndPlatformOrderByRefreshId(String userEmail, Platform platform);

}