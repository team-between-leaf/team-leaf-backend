package com.team.leaf.user.account.entity.controller;

import com.team.leaf.user.account.entity.dto.RegistRequest;
import com.team.leaf.user.account.entity.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/account/regist")
    public ResponseEntity regist(@RequestBody RegistRequest request){
        accountService.regist(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/account/login")
    public ResponseEntity login(@RequestBody RegistRequest request) throws Exception {
        return ResponseEntity.ok(accountService.login(request));
    }
}
