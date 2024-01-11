package com.team.leaf.common.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessage {

    MISMATCHED_ACCOUNT("아이디나 비밀번호를 확인해주세요."),
    ACCOUNT_EXISTS("이미 존재하는 계정입니다.");

    private String message;

}
