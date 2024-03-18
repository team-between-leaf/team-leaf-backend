package com.team.leaf.user.account.dto.request.jwt;

import lombok.Data;

@Data
public class ReIssueDto {
    private String accessToken;
    private String refreshToken;
}
