package com.team.leaf.user.account.controller;

import com.team.leaf.user.account.dto.request.JoinRequest;
import com.team.leaf.user.account.dto.request.LoginRequest;
import com.team.leaf.user.account.dto.response.AccountDto;
import com.team.leaf.user.account.dto.response.GlobalResDto;
import com.team.leaf.user.account.dto.response.LoginAccountDto;
import com.team.leaf.user.account.dto.response.TokenDto;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.jwt.JwtTokenUtil;
import com.team.leaf.user.account.jwt.PrincipalDetails;
import com.team.leaf.user.account.service.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/join")
    public ApiResponse<String> join(@RequestBody JoinRequest joinRequest) throws IOException {

        return new ApiResponse<>(accountService.join(joinRequest));
    }

    @GetMapping(value = "/join/{phone}")
    public ResponseEntity<Boolean> phoneCheck(@PathVariable String phone) {
        return ResponseEntity.ok(accountService.checkPhoneDuplicate(phone));
    }

    @PostMapping("/login")
    public ApiResponse<LoginAccountDto> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
        return new ApiResponse<>(accountService.login(loginRequest, response));
    }

    @DeleteMapping("/logout/{userEmail}")
    public ApiResponse<String> logout(@PathVariable String userEmail) {
        return new ApiResponse<>(accountService.logout(userEmail));
    }

    @PostMapping("/issue/token")
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
    public ResponseEntity findAccountById(@PathVariable String userEmail) {
        AccountDto account = accountService.getAccount(userEmail);

        return ResponseEntity.ok(account);
    }

}
