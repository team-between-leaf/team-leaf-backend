package com.team.leaf.user.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OAuth2LoginResponse {

    private boolean loginSuccess;
    private long oauthId;
    private String email;
    private String name;
    private String birthday;
    private String phone;

}
