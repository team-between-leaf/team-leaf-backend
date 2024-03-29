package com.team.leaf.user.account.service;

import com.team.leaf.user.account.config.SecurityConfig;
import com.team.leaf.user.account.dto.request.jwt.*;
import com.team.leaf.user.account.dto.response.LoginAccountDto;
import com.team.leaf.user.account.dto.response.TokenDto;
import com.team.leaf.user.account.entity.*;
import com.team.leaf.user.account.jwt.JwtTokenUtil;
import com.team.leaf.user.account.repository.AccountRepository;
import com.team.leaf.user.account.repository.InterestCategoryRepository;
import com.team.leaf.user.account.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.team.leaf.user.account.jwt.JwtTokenUtil.*;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CommonService commonService;
    private final InterestCategoryRepository interestCategoryRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final SecurityConfig jwtSecurityConfig;
    private final RedisTemplate redisTemplate;

    public void validatePassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{9,22}$";
        Pattern pwPattern = Pattern.compile(passwordRegex);
        Matcher pwMatcher = pwPattern.matcher(password);

        if (!pwMatcher.matches()) {
            throw new RuntimeException("비밀번호는 최소 9~20자로 구성되어야 하며, 숫자, 영어 대문자, 영어 소문자, 특수 문자를 모두 포함해야 합니다.");
        }
    }

    @Transactional
    public String join(JwtJoinRequest request) {

        commonService.validateEmail(request.getEmail());
        validatePassword(request.getPassword());
        commonService.validatePhone(request.getPhone());

        if(!request.getPassword().equals(request.getPasswordCheck())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String existingPhone = commonService.checkPhoneNumberDuplicate(request.getPhone());
        if (!"중복된 데이터가 없습니다.".equals(existingPhone)) {
            throw new RuntimeException(existingPhone);
        }

        String existingEmail = commonService.checkEmailDuplicate(request.getEmail());
        if (!"중복된 데이터가 없습니다.".equals(existingEmail)) {
            throw new RuntimeException(existingEmail);
        }

        // 닉네임 중복 체크
        String nickName = createNickName(request.getEmail());

        AccountDetail accountDetail = AccountDetail.joinAccount(request.getEmail(),jwtSecurityConfig.passwordEncoder().encode(request.getPassword()), request.getPhone(), nickName);
        accountRepository.save(accountDetail);
        return "Success Join";
    }

    private String createNickName(String email) {
        while(true) {
            String random = generateRandomNumber(7);

            String randomNickName = email.substring(0 , 4) + random;
            if(!accountRepository.existsByNickname(randomNickName)) {
                return randomNickName;
            }
        }
    }

    private String generateRandomNumber(int N) {
        String result = "";
        for(int i = 0; i < N; i++) {
            result += Integer.toString((int) ((Math.random()*10000)%10));
        }

        return result;
    }

    public String joinWithAdditionalInfo(AdditionalJoinInfoRequest request) {

        AccountDetail accountDetail = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        accountDetail.updateAdditionalInfo(request.getName(), request.getBirthday(), request.getGender(), request.getUniversityName());

        List<String> selectedCategories = request.getInterestCategories();

        for(String selectedCategory : selectedCategories) {
            InterestCategory interestCategory = new InterestCategory();
            interestCategory.setCategory(selectedCategory);

            interestCategory = interestCategoryRepository.save(interestCategory);

            AccountInterest accountInterest = new AccountInterest(accountDetail, interestCategory);
            accountDetail.getAccountInterests().add(accountInterest);
        }

        accountRepository.save(accountDetail);

        return "Success";

    }

    @Transactional
    public LoginAccountDto login(JwtLoginRequest request, HttpServletResponse response) {
        // 이메일로 유저 정보 확인
        AccountDetail accountDetail = accountRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new RuntimeException("사용자를 찾을 수 없습니다."));

        // 비밀번호 일치 확인
        if(!jwtSecurityConfig.passwordEncoder().matches(request.getPassword(), accountDetail.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        else {
            TokenDto tokenDto = jwtTokenUtil.createToken(request.getPlatform(), request.getEmail());

            redisTemplate.opsForValue().set("RT:" + request.getEmail(), tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

            commonService.setHeader(response, tokenDto);

            String access_token = tokenDto.getAccessToken();
            String refresh_token = tokenDto.getRefreshToken();

            return new LoginAccountDto(accountDetail.getEmail(),access_token,refresh_token);
        }
    }

    @Transactional
    public String logout(String accessToken, String refreshToken) {
        if(!jwtTokenUtil.tokenValidation(accessToken)) {
            throw new IllegalArgumentException("Invalid Access Token");
        }

        String email = jwtTokenUtil.getEmailFromToken(accessToken);

        if (redisTemplate.opsForValue().get("RT:" + email) != null) {
            redisTemplate.delete("RT:" + email);
        }

        Long expiration = jwtTokenUtil.getExpiration(accessToken);
        refreshTokenRepository.deleteByRefreshToken(refreshToken);

        redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);

        return "Success Logout";
    }

    @Transactional
    public TokenDto refreshAccessToken(Platform platform, String refreshToken) {
        if (!jwtTokenUtil.refreshTokenValidation(refreshToken, platform)) {
            throw new RuntimeException("Invalid Refresh Token");
        }

        String email = jwtTokenUtil.getEmailFromToken(refreshToken);
        if(!refreshToken.equals(refreshToken)) {
            throw new IllegalArgumentException("Refresh Token 정보가 일치하지 않습니다");
        }

        String new_accessToken = jwtTokenUtil.recreateAccessToken(refreshToken);

        redisTemplate.opsForValue().set("RT:" + email, new_accessToken, ACCESS_TIME , TimeUnit.MILLISECONDS);

        return TokenDto.builder().accessToken(new_accessToken).build();
    }

}
