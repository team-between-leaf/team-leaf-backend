package com.team.leaf.user.account.service;

import com.team.leaf.user.account.dto.response.TokenDto;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.entity.OAuth2Account;
import com.team.leaf.user.account.jwt.JwtTokenUtil;
import com.team.leaf.user.account.repository.AccountRepository;
import com.team.leaf.user.account.repository.OAuth2Repository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final OAuth2Repository oAuth2Repository;
    private final AccountRepository accountRepository;

    public void validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(email);

        if (!emailMatcher.matches()) {
            throw new RuntimeException("이메일 형식이 올바르지 않습니다.");
        }
    }

    public void validatePhone(String phone) {
        String phoneRegex = "^[0-9]{10,11}$";
        Pattern phonePattern = Pattern.compile(phoneRegex);
        Matcher phoneMatcher = phonePattern.matcher(phone);

        if(!phoneMatcher.matches()) {
            throw new RuntimeException("전화번호 형식이 올바르지 않습니다.");
        }
    }

    public String checkPhoneNumberDuplicate(String phone) {
        OAuth2Account oAuth2Account = oAuth2Repository.findByPhone(phone);
        AccountDetail accountDetail = accountRepository.findByPhone(phone);

        if (oAuth2Account != null) {
            String socialType = oAuth2Account.getSocialType();
            return socialType + "로그인으로 가입된 계정이 존재합니다.";
        }

        if (accountDetail != null) {
            return "일반 로그인으로 가입된 계정이 존재합니다.";
        }

        return "중복된 데이터가 없습니다.";
    }

    public String checkEmailDuplicate(String email) {
        Optional<OAuth2Account> oAuth2AccountOptional = oAuth2Repository.findByEmail(email);
        Optional<AccountDetail> accountDetailOptional = accountRepository.findByEmail(email);


        if (oAuth2AccountOptional.isPresent()) {
            OAuth2Account oAuth2Account = oAuth2AccountOptional.get();
            String socialType = oAuth2Account.getSocialType();
            return socialType + "로그인으로 가입된 계정이 존재합니다.";
        }

        if (accountDetailOptional.isPresent()) {
            return "일반 로그인으로 가입된 계정이 존재합니다.";
        }

        return "중복된 데이터가 없습니다.";
    }

    public void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtTokenUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtTokenUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

}

