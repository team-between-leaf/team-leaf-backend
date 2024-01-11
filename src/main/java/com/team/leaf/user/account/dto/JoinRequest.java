package com.team.leaf.user.account.dto;

import com.team.leaf.user.account.entity.AccountDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinRequest {

    private String email;

    private String password;

    public static JoinRequest createRequest(AccountDetail detail) {
        return JoinRequest.builder()
                .email(detail.getEmail())
                .password(detail.getPassword())
                .build();
    }

}
