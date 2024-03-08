package com.team.leaf.user.account.dto.request.oauth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateOAuth2Dto {

    private String email;
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