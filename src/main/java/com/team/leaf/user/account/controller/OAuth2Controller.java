package com.team.leaf.user.account.controller;

import com.team.leaf.user.account.dto.request.oauth.OAuth2JoinRequest;
import com.team.leaf.user.account.dto.request.oauth.OAuth2LoginRequest;
import com.team.leaf.user.account.dto.response.LoginAccountDto;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.service.CommonService;
import com.team.leaf.user.account.service.OAuth2Service;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/oauth2")
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;
    private final CommonService commonService;

    @PostMapping("/join")
    @Operation(summary= "소셜 로그인 회원가입 API")
    public ApiResponse<String> join(@RequestBody OAuth2JoinRequest joinRequest) throws IOException {

        return new ApiResponse<>(oAuth2Service.join(joinRequest));
    }

    @PostMapping("/login")
    @Operation(summary= "소셜 로그인 로그인 API")
    public ApiResponse<LoginAccountDto> login(@RequestBody @Valid OAuth2LoginRequest loginRequest, HttpServletResponse response) {
        return new ApiResponse<>(oAuth2Service.login(loginRequest, response));
    }

    @DeleteMapping("/logout/{userEmail}")
    @Operation(summary= "소셜 로그인 로그아웃 API")
    public ApiResponse<String> logout(@PathVariable String userEmail) {
        return new ApiResponse<>(oAuth2Service.logout(userEmail));
    }

    @PostMapping("/check/{phone}")
    @Operation(summary= "중복 회원 확인 API")
    public ResponseEntity<String> checkPhoneDuplicate(@PathVariable String phone) {
        String existingUserMessage = commonService.checkPhoneNumberDuplicate(phone);

        // 해당 번호로 가입된 계정이 존재하는 경우
        if (!"중복된 데이터가 없습니다.".equals(existingUserMessage)) {
            return ResponseEntity.badRequest().body(existingUserMessage);
        }

        return ResponseEntity.ok("중복된 데이터가 없습니다.");
    }

}
