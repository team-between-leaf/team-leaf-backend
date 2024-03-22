package com.team.leaf.user.account.dto.request.oauth;

import lombok.Data;

@Data
public class OAuth2TokenDto {

    private String access_token;
    private String token_type;
    //private String refresh_token;
    private String id_token;
    private int expires_in;
    //private int refresh_token_expires_in;
    private String scope;

}