package com.team.leaf.user.account.controller;

import com.team.leaf.user.account.dto.request.jwt.PlatformRequest;
import com.team.leaf.user.account.dto.request.oauth.OAuth2LoginType;
import com.team.leaf.user.account.dto.request.oauth.OAuthLoginRequest;
import com.team.leaf.user.account.dto.response.OAuth2LoginResponse;
import com.team.leaf.user.account.dto.response.TokenDto;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.jwt.JwtTokenFilter;
import com.team.leaf.user.account.jwt.JwtTokenUtil;
import com.team.leaf.user.account.service.CommonService;
import com.team.leaf.user.account.service.WebOAuth2Service;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/account/oauth2/web")
public class WebOAuth2Controller {

    private final WebOAuth2Service oAuthService;
    private final CommonService commonService;

    @PostMapping("/login")
    @Operation(summary = "[웹 전용] 소셜 로그인 로그인 API")
    public OAuth2LoginResponse login(@RequestBody OAuthLoginRequest request, HttpServletResponse response) {
        return oAuthService.oAuth2Login(request, response);
    }

    @DeleteMapping("/logout")
    @Operation(summary = "소셜 로그인 로그아웃 API")
    public ApiResponse<String> logout(@RequestHeader(name = JwtTokenUtil.ACCESS_TOKEN) String accessToken,
                                      @RequestHeader(name = JwtTokenUtil.REFRESH_TOKEN) String refreshToken) {
        return new ApiResponse<>(oAuthService.logout(accessToken, refreshToken));
    }

    @PostMapping("/issue/token")
    @Operation(summary= "Access Token 갱신 API")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request, HttpServletResponse response,
                                                @RequestBody @RequestHeader(name = JwtTokenUtil.REFRESH_TOKEN, required = false) String refreshToken,
                                                @RequestBody PlatformRequest platformRequest) {
        TokenDto newTokenDto = null;
        try {
            if(refreshToken == null) {
                String cookie_refreshToken = JwtTokenFilter.getTokenByRequest(request, "refreshToken");

                newTokenDto = oAuthService.refreshAccessToken(platformRequest.getPlatform(), cookie_refreshToken);
            } else {
                newTokenDto = oAuthService.refreshAccessToken(platformRequest.getPlatform(), refreshToken);
            }

            commonService.setHeader(response, newTokenDto);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to refresh access token", HttpStatus.UNAUTHORIZED);
        }
    }

}
