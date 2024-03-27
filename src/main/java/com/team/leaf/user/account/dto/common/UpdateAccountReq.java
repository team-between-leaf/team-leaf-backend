package com.team.leaf.user.account.dto.common;

import com.team.leaf.user.account.dto.request.jwt.Platform;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateAccountReq {

    private Platform platform;
    private String email;

    private String previousPasswordCheck;

    private String password;

    private String newPasswordCheck;

    private String name;

    private String nickname;

    private String phone;

    private String birthday;

    private String birthyear;

    private String shippingAddress;

}
