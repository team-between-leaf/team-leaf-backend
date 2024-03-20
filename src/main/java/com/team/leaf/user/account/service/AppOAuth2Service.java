package com.team.leaf.user.account.service;

import com.team.leaf.user.account.dto.request.oauth.OAuth2LoginType;
import com.team.leaf.user.account.dto.response.OAuth2LoginResponse;
import com.team.leaf.user.account.dto.response.TokenDto;
import com.team.leaf.user.account.entity.AccountRole;
import com.team.leaf.user.account.entity.OAuth2Account;
import com.team.leaf.user.account.exception.AccountException;
import com.team.leaf.user.account.jwt.JwtTokenUtil;
import com.team.leaf.user.account.oauth.OAuth2LoginInfo;
import com.team.leaf.user.account.repository.OAuth2Repository;
import com.team.leaf.user.account.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppOAuth2Service {

    private final OAuth2Repository oAuth2Repository;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CommonService commonService;
    private final List<OAuth2LoginInfo> oAuth2LoginInfoList;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate redisTemplate;

    private OAuth2LoginInfo findOAuth2LoginType(OAuth2LoginType type) {
        return oAuth2LoginInfoList.stream()
                .filter(x -> x.type() == type)
                .findFirst()
                .orElseThrow(() -> new AccountException("알 수 없는 로그인 타입입니다."));
    }

    public OAuth2LoginResponse oAuth2Login(OAuth2LoginType type, String accessToken, HttpServletResponse response) {
        OAuth2LoginInfo oAuth2LoginInfo = findOAuth2LoginType(type);

        ResponseEntity<String> userInfoRes = oAuth2LoginInfo.requestUserInfoForApp(accessToken);

        OAuth2Account oAuth2Account = oAuth2LoginInfo.getUserInfo(userInfoRes);

        OAuth2Account existOwner = oAuth2Repository.findByEmail(oAuth2Account.getEmail()).orElse(null);

        commonService.checkPhoneNumberDuplicate(oAuth2Account.getPhone());
        commonService.checkEmailDuplicate(oAuth2Account.getEmail());

        if(existOwner == null) {
            log.info("첫 로그인 회원");
            oAuth2Account.setRole(AccountRole.USER);
            oAuth2Account.setSocialType(type);
            oAuth2Repository.save(oAuth2Account);
        }

        TokenDto tokenDto = jwtTokenUtil.createToken(oAuth2Account.getEmail());

        redisTemplate.opsForValue().set("RT:" + oAuth2Account.getEmail(), tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        commonService.setHeader(response, tokenDto);

        return new OAuth2LoginResponse(true, existOwner == null ? oAuth2Account.getOauthId() : existOwner.getOauthId(), oAuth2Account.getEmail(), oAuth2Account.getName(), oAuth2Account.getBirthday(), oAuth2Account.getPhone());
    }

    @Transactional
    public TokenDto refreshAccessToken(String accessToken, String refreshToken) {
        if (!jwtTokenUtil.tokenValidataion(refreshToken)) {
            throw new RuntimeException("Invalid Refresh Token");
        }

        String email = jwtTokenUtil.getEmailFromToken(accessToken);

        String getRefreshToken = (String)redisTemplate.opsForValue().get("RT:" + email);

        if(ObjectUtils.isEmpty(getRefreshToken)) {
            throw new IllegalArgumentException("Refresh Token이 존재하지 않습니다");
        }
        if(!refreshToken.equals(refreshToken)) {
            throw new IllegalArgumentException("Refresh Token 정보가 일치하지 않습니다");
        }

        TokenDto tokenDto = jwtTokenUtil.createToken(email);


        redisTemplate.opsForValue().set("RT:" + email, tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return tokenDto;
    }

    @Transactional
    public String logout(String accessToken) {
        if(!jwtTokenUtil.tokenValidataion(accessToken)) {
            throw new IllegalArgumentException("Invalid Access Token");
        }

        String email = jwtTokenUtil.getEmailFromToken(accessToken);

        if (redisTemplate.opsForValue().get("RT:" + email) != null) {
            redisTemplate.delete("RT:" + email);
        }

        Long expiration = jwtTokenUtil.getExpiration(accessToken);
        redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);


        return "Success Logout";
    }

}
