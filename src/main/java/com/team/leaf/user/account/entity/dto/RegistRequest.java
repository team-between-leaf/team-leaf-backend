package com.team.leaf.user.account.entity.dto;

import com.team.leaf.user.account.entity.AccountDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegistRequest {

    private String email;

    private String password;

    public static RegistRequest createRequest(AccountDetail detail){
        return RegistRequest.builder()
                .email(detail.getEmail())
                .password(detail.getPassword())
                .build();
    }
}
