package com.team.leaf.user.account.service;

import com.team.leaf.user.account.dto.request.JoinRequest;
import com.team.leaf.user.account.dto.request.LoginRequest;
import com.team.leaf.user.account.dto.request.UpdateAccountDto;
import com.team.leaf.user.account.dto.response.AccountDto;
import com.team.leaf.user.account.dto.response.LoginAccountDto;
import com.team.leaf.user.account.dto.response.TokenDto;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.entity.RefreshToken;
import com.team.leaf.user.account.jwt.JwtTokenUtil;
import com.team.leaf.user.account.repository.AccountRepository;
import com.team.leaf.user.account.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder encoder;

    // 전화번호 중복 확인
    public boolean checkPhoneDuplicate(String phone) {
        return accountRepository.existsByPhone(phone);
    }

    @Transactional
    public String join(JoinRequest request) {

        if (request.getPassword() == null) {
            throw new RuntimeException("Passwords are null");
        }

        if(request.getPasswordCheck() == null) {
            throw new RuntimeException("PasswordChecks are null");
        }

        if(!request.getPassword().equals(request.getPasswordCheck())) {
            throw new RuntimeException("Not matches password and passwordcheck");
        }

        // 번호 중복 체크
        if (accountRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Account with this phone number already exists");
        }

        AccountDetail accountDetail = AccountDetail.joinAccount(request.getEmail(),encoder.encode(request.getPassword()), request.getPhone(), request.getNickname());
        accountRepository.save(accountDetail);
        return "Success Join";
    }

    @Transactional
    public LoginAccountDto login(LoginRequest request, HttpServletResponse response) {

        AccountDetail accountDetail = accountRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new RuntimeException("Not found user"));

        if(!encoder.matches(request.getPassword(), accountDetail.getPassword())) {
            throw new RuntimeException("Not matches Password");
        }

        else {
            TokenDto tokenDto = jwtTokenUtil.createAllToken(request.getEmail());

            Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserEmail(request.getEmail());

            if(refreshToken.isPresent()) {
                refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
            }
            else {
                RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), request.getEmail());
                refreshTokenRepository.save(newToken);
            }

            setHeader(response, tokenDto);

            String access_token = tokenDto.getAccessToken();
            String refresh_token = tokenDto.getRefreshToken();

            return new LoginAccountDto(accountDetail.getEmail(),access_token,refresh_token);
        }
    }

    //유저 정보 수정
    @Transactional
    public String updateUser(String email, UpdateAccountDto accountDto) {
        AccountDetail accountDetail = accountRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("Not found user"));

        accountDetail.updateAccount(accountDto.getEmail(), accountDto.getPassword(), accountDto.getName(), accountDto.getNickname(), accountDto.getPhone(), accountDto.getBirthday(), accountDto.getBirthyear(), accountDto.getUniversityName(), accountDto.getShippingAddress(), accountDto.getSchoolAddress(), accountDto.getWorkAddress());

        return "Success updateUser";
    }

    //유저 정보 조회
    public AccountDto getAccount(String email) {
        AccountDetail accountDetail = accountRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("Not found user"));
        return new AccountDto(accountDetail);
    }

    @Transactional
    public String logout(String email) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserEmail(email).orElseThrow(() ->
                new RuntimeException("Not found login user"));
        refreshTokenRepository.delete(refreshToken);

        return "Success Logout";
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtTokenUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtTokenUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

}
