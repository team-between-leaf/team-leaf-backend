package com.team.leaf.user.account.service;

import com.team.leaf.shopping.follow.repository.FollowRepository;
import com.team.leaf.user.account.config.SecurityConfig;
import com.team.leaf.user.account.dto.common.ShippingAddressReq;
import com.team.leaf.user.account.dto.common.UpdateAccountReq;
import com.team.leaf.user.account.dto.request.jwt.Platform;
import com.team.leaf.user.account.dto.request.jwt.PlatformRequest;
import com.team.leaf.user.account.dto.response.ShippingAddressRes;
import com.team.leaf.user.account.dto.response.TokenDto;
import com.team.leaf.user.account.dto.response.UpdateAccountRes;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.entity.ShippingAddress;
import com.team.leaf.user.account.exception.AccountException;
import com.team.leaf.user.account.jwt.JwtTokenUtil;
import com.team.leaf.user.account.repository.AccountRepository;
import com.team.leaf.user.account.repository.AddressRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountPageService {

    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;
    private final FollowRepository followRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final SecurityConfig securityConfig;
    private final AccountService accountService;
    private final CommonService commonService;
    private final RedisTemplate redisTemplate;

    @Transactional
    public UpdateAccountRes updateAccount(AccountDetail account, HttpServletResponse response, UpdateAccountReq accountDto) {

        AccountDetail accountDetail = accountRepository.findById(account.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (accountDto.getPassword() != null && !accountDto.getPassword().isEmpty()) {
            if (securityConfig.passwordEncoder().matches(accountDto.getPreviousPasswordCheck(), accountDetail.getPassword())) {
                if (accountDto.getPassword().equals(accountDto.getNewPasswordCheck())) {
                    if (accountDto.getPassword().equals(accountDto.getPreviousPasswordCheck())) {
                        throw new AccountException("새로운 비밀번호는 현재 비밀번호와 달라야 합니다.");
                    }
                    accountService.validatePassword(accountDto.getPassword());
                    String encodedPassword = securityConfig.passwordEncoder().encode(accountDto.getPassword());
                    accountDetail.setPassword(encodedPassword);
                } else {
                    throw new AccountException("비밀번호가 일치하지 않습니다.");
                }
            } else {
                throw new AccountException("현재 비밀번호와 일치하지 않습니다.");
            }
        }

        if (accountDto.getName() != null && !accountDto.getName().isEmpty()) {
            accountDetail.setName(accountDto.getName());
        }

        if (accountDto.getNickname() != null && !accountDto.getNickname().isEmpty()) {
            // 닉네임 중복 처리 어떻게 할지 정해지면 중복 처리 추가
            accountDetail.setNickname(accountDto.getNickname());
        }

        if (accountDto.getPhone() != null && !accountDto.getPhone().isEmpty()) {
            commonService.validatePhone(accountDetail.getPhone());
            String existingPhone = commonService.checkPhoneNumberDuplicate(accountDto.getPhone());
            if (!"중복된 데이터가 없습니다.".equals(existingPhone)) {
                throw new RuntimeException(existingPhone);
            }
            accountDetail.setPhone(accountDto.getPhone());
        }

        if (accountDto.getBirthday() != null && !accountDto.getBirthday().isEmpty()) {
            accountDetail.setBirthday(accountDto.getBirthday());
        }

        if (accountDto.getBirthyear() != null && !accountDto.getBirthyear().isEmpty()) {
            accountDetail.setBirthyear(accountDto.getBirthyear());
        }

        accountRepository.save(accountDetail);

        if (accountDto.getEmail() != null && !accountDto.getEmail().isEmpty()) {
            commonService.validateEmail(accountDto.getEmail());
            String existingEmail = commonService.checkEmailDuplicate(accountDto.getEmail());
            if (!"중복된 데이터가 없습니다.".equals(existingEmail)) {
                throw new RuntimeException(existingEmail);
            }
            accountDetail.setEmail(accountDto.getEmail());

            TokenDto tokenDto = jwtTokenUtil.createToken(accountDto.getPlatform(), accountDto.getEmail());

            redisTemplate.opsForValue().set("RT:" + accountDto.getEmail(), tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

            commonService.setHeader(response, tokenDto);

            accountRepository.save(accountDetail);

            return new UpdateAccountRes("Success Update", tokenDto.getAccessToken(), tokenDto.getRefreshToken());
        }

        return new UpdateAccountRes("Success Update", null, null);
    }

    @Transactional
    public String updateShippingAddress(AccountDetail account, ShippingAddressReq updateAddress) {
        AccountDetail accountDetail = accountRepository.findById(account.getUserId()).get();
        Long userId = accountDetail.getUserId();

        Optional<ShippingAddress> existingAddress = addressRepository.findByAccountIdAndAddress(userId, updateAddress.getAddress());
        ShippingAddress shippingAddress = new ShippingAddress(userId);

        if (existingAddress.isPresent()) {
            existingAddress.get().updateShippingAddress(updateAddress.getRecipient(), updateAddress.getPhone(), updateAddress.getAddress(), updateAddress.getDetailedAddress(), updateAddress.getDefaultAddress());
        } else {
            shippingAddress.updateShippingAddress(updateAddress.getRecipient(), updateAddress.getPhone(), updateAddress.getAddress(), updateAddress.getDetailedAddress(), updateAddress.getDefaultAddress());
        }

        addressRepository.save(existingAddress.orElse(shippingAddress));

        return "Success";
    }
    public List<ShippingAddressRes> getAllShippingAddress(AccountDetail account) {

        AccountDetail accountDetail = accountRepository.findById(account.getUserId()).get();
        Long userId = accountDetail.getUserId();

        List<ShippingAddress> shippingAddresses = addressRepository.findAllByAccountId(userId);

        List<ShippingAddressRes> responses = new ArrayList<>();
        for (ShippingAddress address : shippingAddresses) {
            responses.add(new ShippingAddressRes(address));
        }

        return responses;
    }

    public String deleteShippingAddress(Long addressId) {
        addressRepository.deleteById(addressId);

        return "Success";
    }

}

