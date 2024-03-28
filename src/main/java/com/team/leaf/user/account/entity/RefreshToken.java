package com.team.leaf.user.account.entity;

import com.team.leaf.user.account.dto.request.jwt.Platform;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshId;
    @NotBlank
    @Column(nullable = false)
    private String refreshToken;
    @NotBlank
    @Column(nullable = false)
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Platform platform;

    public RefreshToken(String token, String email, Platform platform) {
        this.refreshToken = token;
        this.userEmail = email;
        this.platform = platform;
    }

    public RefreshToken(String token, String email) {
        this.refreshToken = token;
        this.userEmail = email;
    }

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }

}