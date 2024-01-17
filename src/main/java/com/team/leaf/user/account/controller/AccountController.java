package com.team.leaf.user.account.controller;

import com.team.leaf.user.account.dto.AccountRequest;
import com.team.leaf.user.account.dto.AccountResponse;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/account/join")
    public ResponseEntity join(@RequestBody AccountRequest request) {
        accountService.signUpAccount(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/account/login")
    public ResponseEntity login(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountService.login(request));
    }

    @GetMapping("/account/{userEmail}")
    public ResponseEntity findAccountById(@PathVariable String userEmail) {
        AccountDetail account = accountService.findAccountById(userEmail);

        return ResponseEntity.ok(AccountResponse.createRequest(account));
    }

}
