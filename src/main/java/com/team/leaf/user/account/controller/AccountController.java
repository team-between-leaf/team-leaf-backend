package com.team.leaf.user.account.controller;

import com.team.leaf.user.account.dto.common.DuplicateEmailRequest;
import com.team.leaf.user.account.dto.common.DuplicatePhoneRequest;
import com.team.leaf.user.account.dto.request.jwt.AdditionalJoinInfoRequest;
import com.team.leaf.user.account.dto.request.jwt.JwtJoinRequest;
import com.team.leaf.user.account.dto.request.jwt.JwtLoginRequest;
import com.team.leaf.user.account.dto.response.LoginAccountDto;
import com.team.leaf.user.account.dto.response.TokenDto;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.jwt.JwtTokenFilter;
import com.team.leaf.user.account.jwt.JwtTokenUtil;
import com.team.leaf.user.account.service.AccountService;
import com.team.leaf.user.account.service.CommonService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final CommonService commonService;

    @PostMapping("/join")
    @Operation(summary= "자체 로그인 회원가입 API")
    public ApiResponse<String> join(@RequestBody JwtJoinRequest joinRequest) throws IOException {

        return new ApiResponse<>(accountService.join(joinRequest));
    }

    @PostMapping("/join/additional-info")
    @Operation(summary = "[웹 전용] 회원가입 추가 정보 입력 API")
    public ApiResponse<String> joinWithAdditionalInfo(@RequestBody AdditionalJoinInfoRequest request) throws IOException {
        String responseMessage = accountService.joinWithAdditionalInfo(request);
        return new ApiResponse<>(responseMessage);
    }

    @PostMapping("/login")
    @Operation(summary= "자체 로그인 로그인 API")
    public ApiResponse<LoginAccountDto> login(@RequestBody @Valid JwtLoginRequest loginRequest, HttpServletResponse response) {
        return new ApiResponse<>(accountService.login(loginRequest, response));
    }

    @DeleteMapping("/logout")
    @Operation(summary = "자체 로그인 로그아웃 API")
    public ApiResponse<String> logout(@RequestHeader(name = JwtTokenUtil.ACCESS_TOKEN, required = false) String accessToken) {
        return new ApiResponse<>(accountService.logout(accessToken));
    }

    @PostMapping("/issue/token")
    @Operation(summary= "Access Token 갱신 API")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request, HttpServletResponse response,
                                                @RequestHeader(name = JwtTokenUtil.REFRESH_TOKEN, required = false) String refreshToken) {
        try {
            TokenDto newTokenDto = null;

            if(refreshToken == null) {
                String cookie_refreshToken = JwtTokenFilter.getTokenByRequest(request, "refreshToken");

                newTokenDto = accountService.refreshAccessToken(cookie_refreshToken);
            } else {
                newTokenDto = accountService.refreshAccessToken(refreshToken);
            }

            commonService.setHeader(response, newTokenDto);

            return new ResponseEntity<>(newTokenDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to refresh access token", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/email")
    @Operation(summary = "중복 이메일 확인 API")
    public ResponseEntity<String> findAccountById(@RequestBody DuplicateEmailRequest emailRequest) {
        String existingUserMessage = commonService.checkEmailDuplicate(emailRequest.getEmail());

        if (!"중복된 데이터가 없습니다.".equals(existingUserMessage)) {
            return ResponseEntity.badRequest().body(existingUserMessage);
        }

        return ResponseEntity.ok("중복된 데이터가 없습니다.");
    }

    @PostMapping("/check/phone")
    @Operation(summary = "중복 회원 확인 API")
    public ResponseEntity<String> checkPhoneDuplicate(@RequestBody DuplicatePhoneRequest phoneRequest) {
        String existingUserMessage = commonService.checkPhoneNumberDuplicate(phoneRequest.getPhone());

        if (!"중복된 데이터가 없습니다.".equals(existingUserMessage)) {
            return ResponseEntity.badRequest().body(existingUserMessage);
        }

        return ResponseEntity.ok("중복된 데이터가 없습니다.");
    }

}
