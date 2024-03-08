package com.team.leaf.user.account.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import static com.team.leaf.user.account.entity.AccountPrivacy.createAccountPrivacy;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "oauth_account")
public class OAuth2Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long oauthId;

    private String email;

    private String name;

    private String nickname;

    private String birthday;

    private String birthyear;

    private String phone;

    private String universityName;

    private String shippingAddress;

    private String schoolAddress;

    private String workAddress;

    private String socialType;

    private LocalDate joinDate;

    private LocalDate lastAccess;

    private int loginFailCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private AccountPrivacy userDetail;

    public String getSocialType() {
        return socialType;
    }

    public static OAuth2Account joinNaverKakao(String email, String name, String nickname, String phone, String birthday, String socialType) {
        OAuth2Account oAuth2Account = new OAuth2Account();
        oAuth2Account.email = email;
        oAuth2Account.name = name;
        oAuth2Account.phone = phone;
        oAuth2Account.nickname = nickname;
        oAuth2Account.birthday = birthday;
        oAuth2Account.socialType = socialType;
        oAuth2Account.role = AccountRole.USER;
        oAuth2Account.joinDate = LocalDate.now();
        oAuth2Account.userDetail = createAccountPrivacy();

        return oAuth2Account;
    }

    public static OAuth2Account joinGoogle(String email, String name, String nickname, String phone, String socialType) {
        OAuth2Account oAuth2Account = new OAuth2Account();
        oAuth2Account.email = email;
        oAuth2Account.name = name;
        oAuth2Account.phone = phone;
        oAuth2Account.nickname = nickname;
        oAuth2Account.socialType = socialType;
        oAuth2Account.role = AccountRole.USER;
        oAuth2Account.joinDate = LocalDate.now();
        oAuth2Account.userDetail = createAccountPrivacy();

        return oAuth2Account;
    }

}

