package com.team.leaf.user.account.dto.request.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtLoginRequest {

    private String email;
    private String password;

}
