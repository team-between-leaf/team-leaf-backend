package com.team.leaf.user.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "oauth_account")
public class OAuthAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long oauthId;

    private String email;

    private String name;

    private String nickname;

    private String birthday;

    private String birthyear;

    private String phoneNumber;

    private String universityName;

    private String shippingAddress;

    private String schoolAddress;

    private String workAddress;

    private String oauthCategory;

    private LocalDate joinDate;

    private LocalDate lastAccess;

    private int loginFailCount;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;

    public OAuthAccount update(String name, String birthday, String birthyear, String phoneNumber, String oauthCategory) {
        this.name = name;
        this.birthday = birthday;
        this.birthyear = birthyear;
        this.phoneNumber = phoneNumber;
        this.oauthCategory = oauthCategory;

        return this;
    }

    /*public String getRoleKey(){
        return this.role.getKey();
    }*/

    public String getOauthCategory() {
        return this.oauthCategory;
    }

}

