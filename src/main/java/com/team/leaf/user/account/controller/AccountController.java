package com.team.leaf.user.account.controller;

import com.team.leaf.user.account.dto.JoinRequest;
import com.team.leaf.user.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/account/join")
    public ResponseEntity join(@RequestBody JoinRequest request) {
        accountService.signUpAccount(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/account/login")
    public ResponseEntity login(@RequestBody JoinRequest request) throws Exception {
        return ResponseEntity.ok(accountService.login(request));
    }

}
