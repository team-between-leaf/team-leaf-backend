package com.team.leaf.user.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateAccountRes {

    private String msg;

    private String accessToken;

    private String refreshToken;

}