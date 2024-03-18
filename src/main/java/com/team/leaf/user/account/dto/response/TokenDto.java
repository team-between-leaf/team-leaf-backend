package com.team.leaf.user.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class TokenDto {

    private String accessToken;
    private String refreshToken;
    private Long refreshTokenExpirationTime;

}
