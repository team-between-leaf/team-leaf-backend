package com.team.leaf.user.account.dto.request.oauth;

import com.team.leaf.user.account.dto.request.jwt.Platform;
import lombok.Getter;

@Getter
public class OAuthLoginRequest {

    private String code;

    private Platform platform;

    private OAuth2LoginType type;

}
