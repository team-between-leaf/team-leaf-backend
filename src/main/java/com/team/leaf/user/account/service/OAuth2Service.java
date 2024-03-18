package com.team.leaf.user.account.service;

import com.team.leaf.user.account.dto.request.oauth.OAuth2JoinRequest;
import com.team.leaf.user.account.dto.request.oauth.OAuth2LoginRequest;
import com.team.leaf.user.account.dto.response.LoginAccountDto;
import com.team.leaf.user.account.dto.response.OAuth2Dto;
import com.team.leaf.user.account.dto.response.TokenDto;
import com.team.leaf.user.account.entity.AccountRole;
import com.team.leaf.user.account.entity.OAuth2Account;
import com.team.leaf.user.account.entity.RefreshToken;
import com.team.leaf.user.account.jwt.JwtTokenUtil;
import com.team.leaf.user.account.repository.OAuth2Repository;
import com.team.leaf.user.account.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final OAuth2Repository oAuth2Repository;
    private final CommonService commonService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Transactional
    public String join(OAuth2JoinRequest request) {

        commonService.validateEmail(request.getEmail());
        commonService.validatePhone(request.getPhone());

        String existingPhone = commonService.checkPhoneNumberDuplicate(request.getPhone());
        if (!"중복된 데이터가 없습니다.".equals(existingPhone)) {
            throw new RuntimeException(existingPhone);
        }

        String existingEmail = commonService.checkEmailDuplicate(request.getEmail());
        if (!"중복된 데이터가 없습니다.".equals(existingEmail)) {
            throw new RuntimeException(existingEmail);
        }

        if (oAuth2Repository.existsByNickname(request.getNickname())) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }

        if ("naver".equals(request.getSocialType()) || "kakao".equals(request.getSocialType())) {
            OAuth2Account oAuth2Account = OAuth2Account.joinNaverKakao(request.getEmail(), request.getName(), request.getNickname(), request.getPhone(), request.getBirthday(), request.getSocialType());
            oAuth2Repository.save(oAuth2Account);
        }

        if("google".equals(request.getSocialType())) {
            OAuth2Account oAuth2Account = OAuth2Account.joinGoogle(request.getEmail(), request.getName(), request.getNickname(), request.getPhone(), request.getSocialType());
            oAuth2Repository.save(oAuth2Account);
        }

        return "Success Join";
    }

    @Transactional
    public LoginAccountDto login(OAuth2LoginRequest request, HttpServletResponse response) {
        OAuth2Account oAuth2Account = oAuth2Repository.findByEmail(request.getEmail()).orElseThrow(() ->
                new RuntimeException("사용자를 찾을 수 없습니다."));

        TokenDto tokenDto = jwtTokenUtil.createAllToken(request.getEmail());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserEmail(request.getEmail());

        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }
        else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), request.getEmail());
            refreshTokenRepository.save(newToken);
        }

        commonService.setHeader(response, tokenDto);

        String access_token = tokenDto.getAccessToken();
        String refresh_token = tokenDto.getRefreshToken();

        return new LoginAccountDto(oAuth2Account.getEmail(),access_token,refresh_token);
    }

    public OAuth2Dto getAccount(String email) {
        OAuth2Account oAuth2Account = oAuth2Repository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("사용자를 찾을 수 없습니다."));
        return new OAuth2Dto(oAuth2Account);
    }

    @Transactional
    public String logout(String email) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserEmail(email).orElseThrow(() ->
                new RuntimeException("로그인 되어 있지 않은 사용자입니다."));
        refreshTokenRepository.delete(refreshToken);

        return "Success Logout";
    }

}

