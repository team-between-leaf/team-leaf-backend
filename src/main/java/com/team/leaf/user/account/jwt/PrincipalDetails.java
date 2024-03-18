package com.team.leaf.user.account.jwt;

import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.entity.OAuth2Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private AccountDetail accountDetail;
    private OAuth2Account oAuth2Account;

    public void setAccountDetail(AccountDetail accountDetail) {
        this.accountDetail = accountDetail;
    }

    public AccountDetail getAccountDetail() {
        return this.accountDetail;
    }

    public void setOAuth2Account(OAuth2Account oAuth2Account) {
        this.oAuth2Account = oAuth2Account;
    }

    public OAuth2Account getOAuth2Account() {
        return this.oAuth2Account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayList<>();
        if(accountDetail != null) {
            collections.add(() -> {
                return accountDetail.getRole().name();
            });
        } else if(oAuth2Account != null) {
            collections.add(() -> {
                return oAuth2Account.getRole().name();
            });
        }

        return collections;
    }

    @Override
    public String getPassword() {
        if(accountDetail != null) {
            return accountDetail.getPassword();
        }
        return null;
    }

    @Override
    public String getUsername() {
        if(accountDetail != null) {
            return accountDetail.getEmail();
        } else if(oAuth2Account != null) {
            return oAuth2Account.getEmail();
        }
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
