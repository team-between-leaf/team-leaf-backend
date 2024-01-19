package com.team.leaf.user.account.service;

import com.team.leaf.user.account.dto.AccountRequest;
import com.team.leaf.user.account.dto.AccountResponse;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.exception.AccountException;
import com.team.leaf.user.account.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.team.leaf.common.message.ResponseMessage.ACCOUNT_EXISTS;
import static com.team.leaf.common.message.ResponseMessage.MISMATCHED_ACCOUNT;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public void signUpAccount(AccountRequest request) {
        Optional<AccountDetail> user = accountRepository.findByEmail(request.getEmail());
        if(user.isPresent()) {
            throw new AccountException(ACCOUNT_EXISTS.getMessage());
        }

        AccountDetail detail = AccountDetail.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        accountRepository.save(detail);
    }

    public AccountResponse login(AccountRequest request) {
        AccountDetail account = findAccountById(request.getEmail());

        if(!account.getPassword().equals(request.getPassword())) {
            throw new AccountException(MISMATCHED_ACCOUNT.getMessage());
        }

        return AccountResponse.createRequest(account);
    }

    public AccountDetail findAccountById(String email) {
        AccountDetail account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountException(MISMATCHED_ACCOUNT.getMessage()));

        return account;
    }
}
