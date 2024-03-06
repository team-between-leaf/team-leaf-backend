package com.team.leaf.user.account.controller;

import com.team.leaf.user.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/mypage")
public class AccountPageController {

    private final AccountService accountService;

    //mypage 파트 수정 예정....!

    /*//유저 정보 조회
    @GetMapping("/{email}/search")
    public ApiResponse<AccountDto> getAccount(@PathVariable String email) {
        AccountDto accountDto = accountService.getAccount(email);

        return new ApiResponse<>(accountDto);
    }

    //유저 정보 수정
    @PutMapping("/{email}/update")
    public ApiResponse<String> updateAccount(@PathVariable String email, UpdateAccountDto accountDto) throws IOException {
        return new ApiResponse<>(accountService.updateUser(email,accountDto));
    }*/

}

