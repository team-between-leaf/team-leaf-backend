package com.team.leaf.user.account.repository;

import com.team.leaf.user.account.dto.request.jwt.Platform;
import com.team.leaf.user.account.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUserEmailAndRefreshToken(String userEmail, String refreshToken);

    List<RefreshToken> findByUserEmailAndPlatformOrderByRefreshId(String userEmail, Platform platform);

    Optional<RefreshToken> findByRefreshTokenAndPlatformOrderByRefreshId(String refreshToken, Platform platform);

    void deleteByRefreshToken(String refreshToken);

}
