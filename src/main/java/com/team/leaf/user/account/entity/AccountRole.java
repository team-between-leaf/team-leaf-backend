package com.team.leaf.user.account.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountRole {

    ADMIN("ROLE_ADMIN", "관리자"),
    SELLER("ROLE_SELLER", "판매자"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;

}
