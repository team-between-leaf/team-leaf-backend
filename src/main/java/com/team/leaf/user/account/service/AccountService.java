package com.team.leaf.user.account.service;

import com.team.leaf.user.account.dto.JoinRequest;
import com.team.leaf.user.account.entity.AccountDetail;
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
    public void signUpAccount(JoinRequest request) throws Exception {
        Optional<AccountDetail> user = accountRepository.findByEmail(request.getEmail());
        if(user.isPresent()) {
            throw new Exception(ACCOUNT_EXISTS.getMessage());
        }

        AccountDetail detail = AccountDetail.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        accountRepository.save(detail);
    }

    public JoinRequest login(JoinRequest request) throws Exception {
        AccountDetail data = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new Exception(MISMATCHED_ACCOUNT.getMessage()));

        if(!data.getPassword().equals(request.getPassword())) {
            throw new Exception(MISMATCHED_ACCOUNT.getMessage());
        }

        return JoinRequest.createRequest(data);
    }
}
