package com.team.leaf.user.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.team.leaf.user.account.entity.AccountPrivacy.createAccountPrivacy;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String email;

    private String password;

    private String name;

    @Column(unique = true)
    private String nickname;

    private String birthday;

    private String birthyear;

    private String universityName;

    private String shippingAddress;

    private String schoolAddress;

    private String workAddress;

    private LocalDate joinDate;

    private String phone;

    private LocalDate lastAccess;

    private int loginFailCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private AccountPrivacy userDetail;

    public static AccountDetail joinAccount(String email, String encodedPassword, String phone, String nickname) {
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.email = email;
        accountDetail.password = encodedPassword;
        accountDetail.phone = phone;
        accountDetail.nickname = nickname;
        accountDetail.role = AccountRole.USER;
        accountDetail.joinDate = LocalDate.now();
        accountDetail.userDetail = createAccountPrivacy();
        //accountDetail.lastAccess = LocalDate.now();

        return accountDetail;
    }

    //유저 정보 수정
    public void updateAccount(String email, String password, String name, String nickname, String phone, String birthday, String birthyear, String universityName, String shippingAddress, String schoolAddress, String workAddress) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.birthday = birthday;
        this.birthyear = birthyear;
        this.universityName = universityName;
        this.shippingAddress = shippingAddress;
        this.schoolAddress = schoolAddress;
        this.workAddress = workAddress;
    }

}
