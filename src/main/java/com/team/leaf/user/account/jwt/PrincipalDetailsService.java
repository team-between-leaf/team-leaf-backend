package com.team.leaf.user.account.jwt;

import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.entity.OAuth2Account;
import com.team.leaf.user.account.repository.AccountRepository;
import com.team.leaf.user.account.repository.OAuth2Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    public final AccountRepository accountRepository;
    private final OAuth2Repository oAuth2Repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AccountDetail accountDetail = accountRepository.findByEmail(email).orElse(null);
        OAuth2Account oAuth2Account = oAuth2Repository.findByEmail(email).orElse(null);

        PrincipalDetails userDetails = new PrincipalDetails();

        if(accountDetail != null) {
            userDetails.setAccountDetail(accountDetail);
            return userDetails;
        } else if (oAuth2Account != null) {
            userDetails.setOAuth2Account(oAuth2Account);
            return userDetails;
        }

        throw new UsernameNotFoundException("Not fount User");
    }

}
