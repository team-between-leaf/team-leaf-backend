package com.team.leaf.user.account.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateAccountDto {

    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phone;
    private String birthday;
    private String birthyear;
    private String universityName;
    private String shippingAddress;
    private String schoolAddress;
    private String workAddress;

}
