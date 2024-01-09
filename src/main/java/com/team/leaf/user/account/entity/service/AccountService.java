package com.team.leaf.user.account.entity.service;

import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.entity.AccountPrivacy;
import com.team.leaf.user.account.entity.dto.RegistRequest;
import com.team.leaf.user.account.entity.repository.AccountRepository;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public void regist(RegistRequest request) {
        AccountDetail detail = AccountDetail.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        accountRepository.save(detail);
    }

    public RegistRequest login(RegistRequest request) throws Exception {
        AccountDetail data = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new Exception("찾을 수 없어요."));
        if(!data.getPassword().equals(request.getPassword())){
            throw new Exception("비밀번호 일치 x");
        }
        return RegistRequest.createRequest(data);
    }
}