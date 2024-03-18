package com.team.leaf.user.account.oauth;

import com.team.leaf.user.account.dto.request.oauth.OAuth2LoginType;
import com.team.leaf.user.account.dto.request.oauth.OAuth2TokenDto;
import com.team.leaf.user.account.entity.OAuth2Account;
import org.springframework.http.ResponseEntity;

public interface OAuth2LoginInfo {

    ResponseEntity<String> requestAccessToken(String code);
    OAuth2TokenDto getAccessToken(ResponseEntity<String> response);

    ResponseEntity<String> requestUserInfo(OAuth2TokenDto oAuth2TokenDto);

    ResponseEntity<String> requestUserInfoForApp(String accessToken);

    OAuth2Account getUserInfo(ResponseEntity<String> userInfoRes);


    default OAuth2LoginType type() {
        if(this instanceof GoogleLoginInfo) {
            return OAuth2LoginType.GOOGLE;
        } else if (this instanceof KakaoLoginInfo) {
            return OAuth2LoginType.KAKAO;
        } else if (this instanceof NaverLoginInfo) {
            return OAuth2LoginType.NAVER;
        } else {
            return null;
        }
    }

}

