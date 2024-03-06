package com.team.leaf.user.account.dto.request.oauth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OAuth2JoinRequest {

    @NotBlank(message = "로그인 아이디가 비어있습니다.")
    private String email;

    @NotBlank(message = "이름이 비어있습니다.")
    private String name;

    @NotBlank(message = "닉네임이 비어있습니다.")
    private String nickname;

    @NotBlank(message = "핸드폰 번호가 비어있습니다.")
    private String phone;

    @NotBlank(message = "소셜 종류가 비어있습니다.")
    private String socialType;

    private String birthday;

    private String accessToken;


    public OAuth2JoinRequest(String socialType, String email, String name, String nickname, String phone, String birthday, String accessToken) {
        this.socialType = socialType;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.birthday = birthday;
        this.accessToken = accessToken;
    }

}
