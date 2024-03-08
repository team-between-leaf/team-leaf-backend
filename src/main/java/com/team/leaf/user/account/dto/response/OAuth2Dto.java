package com.team.leaf.user.account.dto.response;

import com.team.leaf.user.account.entity.AccountRole;
import com.team.leaf.user.account.entity.OAuth2Account;
import lombok.Getter;

@Getter
public class OAuth2Dto {

    private Long id;
    private String email;
    private String nickname;
    private String name;
    private AccountRole role;
    private String phone;
    private String birthday;
    private String socialType;
    private String universityName;
    private String shippingAddress;
    private String schoolAddress;
    private String workAddress;

    public OAuth2Dto(OAuth2Account oAuth2Account) {
        this.id = oAuth2Account.getOauthId();
        this.nickname = oAuth2Account.getNickname();
        this.email = oAuth2Account.getEmail();
        this.name = oAuth2Account.getName();
        this.role = oAuth2Account.getRole();
        this.phone = oAuth2Account.getPhone();
        this.birthday = oAuth2Account.getBirthday();
        this.socialType =
                this.universityName = oAuth2Account.getUniversityName();
        this.shippingAddress = oAuth2Account.getShippingAddress();
        this.schoolAddress = oAuth2Account.getSchoolAddress();
        this.workAddress = oAuth2Account.getWorkAddress();
    }

}
