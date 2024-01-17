package com.team.leaf.user.account.dto;

import com.team.leaf.user.account.entity.AccountDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    private String email;

    private String password;

    public static AccountResponse createRequest(AccountDetail detail) {
        return AccountResponse.builder()
                .email(detail.getEmail())
                .password(detail.getPassword())
                .build();
    }

}