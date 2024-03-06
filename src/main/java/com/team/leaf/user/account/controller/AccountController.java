package com.team.leaf.user.account.controller;

import com.team.leaf.user.account.dto.request.jwt.JwtJoinRequest;
import com.team.leaf.user.account.dto.request.jwt.JwtLoginRequest;
import com.team.leaf.user.account.dto.response.AccountDto;
import com.team.leaf.user.account.dto.response.LoginAccountDto;
import com.team.leaf.user.account.dto.response.TokenDto;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.jwt.JwtTokenUtil;
import com.team.leaf.user.account.service.AccountService;
import com.team.leaf.user.account.service.CommonService;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping("/login")
    @Operation(summary= "자체 로그인 로그인 API")
    public ApiResponse<LoginAccountDto> login(@RequestBody @Valid JwtLoginRequest loginRequest, HttpServletResponse response) {
        return new ApiResponse<>(accountService.login(loginRequest, response));
    }

    @DeleteMapping("/logout/{userEmail}")
    @Operation(summary= "자체 로그인 로그아웃 API")
    public ApiResponse<String> logout(@PathVariable String userEmail) {
        return new ApiResponse<>(accountService.logout(userEmail));
    }

    @PostMapping("/issue/token")
    @Operation(summary= "Access Token 갱신 API")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader(name = JwtTokenUtil.REFRESH_TOKEN, required = false) String refreshToken) {
        try {
            TokenDto newTokenDto = accountService.refreshAccessToken(refreshToken);

            HttpHeaders headers = new HttpHeaders();
            headers.add(JwtTokenUtil.ACCESS_TOKEN, newTokenDto.getAccessToken());
            headers.add(JwtTokenUtil.REFRESH_TOKEN, newTokenDto.getRefreshToken());

            return new ResponseEntity<>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to refresh access token", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{userEmail}")
    @Operation(summary= "중복 이메일 확인 API")
    public ResponseEntity findAccountById(@PathVariable String userEmail) {
        AccountDto account = accountService.getAccount(userEmail);

        return ResponseEntity.ok(account);
    }

    @PostMapping("/check/{phone}")
    @Operation(summary= "중복 회원 확인 API")
    public ResponseEntity<String> checkPhoneDuplicate(@PathVariable String phone) {
        String existingUserMessage = commonService.checkPhoneNumberDuplicate(phone);

        if (!"중복된 데이터가 없습니다.".equals(existingUserMessage)) {
            return ResponseEntity.badRequest().body(existingUserMessage);
        }

        return ResponseEntity.ok("중복된 데이터가 없습니다.");
    }

}
