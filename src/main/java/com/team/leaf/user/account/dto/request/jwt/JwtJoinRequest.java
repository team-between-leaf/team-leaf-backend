package com.team.leaf.user.account.dto.request.jwt;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class JwtJoinRequest {

    @NotBlank(message = "로그인 아이디가 비어있습니다.")
    private String email;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인이 비어있습니다.")
    private String passwordCheck;

    @NotBlank(message = "닉네임이 비어있습니다.")
    private String nickname;

    @NotBlank(message = "핸드폰 번호가 비어있습니다.")
    private String phone;

    public JwtJoinRequest(String email, String password, String passwordCheck, String nickname, String phone) {
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.nickname = nickname;
        this.phone = phone;
    }

}
