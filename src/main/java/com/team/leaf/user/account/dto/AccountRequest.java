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
public class AccountRequest {

    private String email;

    private String password;

    public static AccountRequest createRequest(AccountDetail detail) {
        return AccountRequest.builder()
                .email(detail.getEmail())
                .password(detail.getPassword())
                .build();
    }

}
