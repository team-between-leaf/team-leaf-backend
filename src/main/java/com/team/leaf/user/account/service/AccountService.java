package com.team.leaf.user.account.service;

import com.team.leaf.user.account.config.JwtSecurityConfig;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtSecurityConfig jwtSecurityConfig;

    // 전화번호 중복 확인
    public boolean checkPhoneDuplicate(String phone) {
        return accountRepository.existsByPhone(phone);
    }

    private void validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(email);

        if (!emailMatcher.matches()) {
            throw new RuntimeException("이메일 형식이 올바르지 않습니다.");
        }
    }

    private void validatePassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{9,20}$";
        Pattern pwPattern = Pattern.compile(passwordRegex);
        Matcher pwMatcher = pwPattern.matcher(password);

        if (!pwMatcher.matches()) {
            throw new RuntimeException("비밀번호는 최소 9~20자로 구성되어야 하며, 숫자, 영어 대문자, 영어 소문자, 특수 문자를 모두 포함해야 합니다.");
        }
    }

    private void validatePhone(String phone) {
        String phoneRegex = "^[0-9]{10,11}$";
        Pattern phonePattern = Pattern.compile(phoneRegex);
        Matcher phoneMatcher = phonePattern.matcher(phone);

        if(!phoneMatcher.matches()) {
            throw new RuntimeException("전화번호 형식이 올바르지 않습니다.");
        }
    }

    @Transactional
    public String join(JoinRequest request) {

        validateEmail(request.getEmail());
        validatePassword(request.getPassword());
        validatePhone(request.getPhone());

        if(!request.getPassword().equals(request.getPasswordCheck())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 번호 중복 체크
        if (accountRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("해당 번호로 가입된 계정이 이미 존재합니다.");
        }

        // 닉네임 중복 체크
        if (accountRepository.existsByNickname(request.getNickname())) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }

        AccountDetail accountDetail = AccountDetail.joinAccount(request.getEmail(),jwtSecurityConfig.passwordEncoder().encode(request.getPassword()), request.getPhone(), request.getNickname());
        accountRepository.save(accountDetail);
        return "Success Join";
    }

    @Transactional
    public LoginAccountDto login(LoginRequest request, HttpServletResponse response) {

        // 이메일로 유저 정보 확인
        AccountDetail accountDetail = accountRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new RuntimeException("사용자를 찾을 수 없습니다."));

        // 비밀번호 일치 확인
        if(!jwtSecurityConfig.passwordEncoder().matches(request.getPassword(), accountDetail.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
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

    @Transactional
    public String updateUser(String email, UpdateAccountDto accountDto) {
        AccountDetail accountDetail = accountRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("사용자를 찾을 수 없습니다."));

        if (accountDto.getEmail() != null && !accountDto.getEmail().isEmpty()) {
            validateEmail(accountDto.getEmail());
            accountDetail.setEmail(accountDto.getEmail());
        }

        if (accountDto.getPassword() != null && !accountDto.getPassword().isEmpty()) {
            validatePassword(accountDto.getPassword());
            String encodedPassword = jwtSecurityConfig.passwordEncoder().encode(accountDto.getPassword());
            accountDetail.setPassword(encodedPassword);
        }

        if (accountDto.getName() != null && !accountDto.getName().isEmpty()) {
            accountDetail.setName(accountDto.getName());
        }

        if (accountDto.getNickname() != null && !accountDto.getNickname().isEmpty()) {
            accountDetail.setNickname(accountDto.getNickname());
        }

        if (accountDto.getPhone() != null && !accountDto.getPhone().isEmpty()) {
            validatePhone(accountDetail.getPhone());
            accountDetail.setPhone(accountDto.getPhone());
        }

        if (accountDto.getBirthday() != null && !accountDto.getBirthday().isEmpty()) {
            accountDetail.setBirthday(accountDto.getBirthday());
        }

        if (accountDto.getBirthyear() != null && !accountDto.getBirthyear().isEmpty()) {
            accountDetail.setBirthyear(accountDto.getBirthyear());
        }

        if (accountDto.getUniversityName() != null && !accountDto.getUniversityName().isEmpty()) {
            accountDetail.setUniversityName(accountDto.getUniversityName());
        }

        if (accountDto.getShippingAddress() != null && !accountDto.getShippingAddress().isEmpty()) {
            accountDetail.setShippingAddress(accountDto.getShippingAddress());
        }

        if (accountDto.getSchoolAddress() != null && !accountDto.getSchoolAddress().isEmpty()) {
            accountDetail.setSchoolAddress(accountDto.getSchoolAddress());
        }

        if (accountDto.getWorkAddress() != null && !accountDto.getWorkAddress().isEmpty()) {
            accountDetail.setWorkAddress(accountDto.getWorkAddress());
        }

        accountRepository.save(accountDetail);

        return "Success updateUser";
    }


    //유저 정보 조회
    @Transactional
    public AccountDto getAccount(String email) {
        AccountDetail accountDetail = accountRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("사용자를 찾을 수 없습니다."));

        return new AccountDto(accountDetail);
    }

    @Transactional
    public String logout(String email) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserEmail(email).orElseThrow(() ->
                new RuntimeException("로그인 되어 있지 않은 사용자입니다."));
        refreshTokenRepository.delete(refreshToken);

        return "Success Logout";
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtTokenUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtTokenUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

    @Transactional
    public TokenDto refreshAccessToken(String refreshToken) {
        // Refresh Token 검증
        if (!jwtTokenUtil.refreshTokenValidation(refreshToken)) {
            throw new RuntimeException("Invalid Refresh Token");
        }

        // Refresh Token에서 이메일 추출
        String email = jwtTokenUtil.getEmailFromToken(refreshToken);

        // 새로운 Access Token 및 Refresh Token 생성
        TokenDto newTokenDto = jwtTokenUtil.createAllToken(email);

        // DB에 저장된 Refresh Token 갱신
        Optional<RefreshToken> existingRefreshToken = refreshTokenRepository.findByUserEmail(email);
        if (existingRefreshToken.isPresent()) {
            existingRefreshToken.get().updateToken(newTokenDto.getRefreshToken());
        } else {
            RefreshToken newRefreshToken = new RefreshToken(newTokenDto.getRefreshToken(), email);
            refreshTokenRepository.save(newRefreshToken);
        }

        return newTokenDto;
    }


}
