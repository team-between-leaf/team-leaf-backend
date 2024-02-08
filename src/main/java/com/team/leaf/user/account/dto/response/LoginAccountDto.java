package com.team.leaf.user.account.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginAccountDto {

    private String userEmail;
    private String accessToken;
    private String refreshToken;

    public LoginAccountDto(String userEmail, String accessToken, String refreshToken) {
        this.userEmail = userEmail;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
