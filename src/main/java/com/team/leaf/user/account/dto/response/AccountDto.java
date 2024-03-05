package com.team.leaf.user.account.dto.response;

import com.team.leaf.user.account.entity.AccountRole;
import com.team.leaf.user.account.entity.AccountDetail;
import lombok.Getter;

@Getter
public class AccountDto {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String name;
    private AccountRole role;
    private String phone;
    private String birthday;
    private String birthyear;
    private String universityName;
    private String shippingAddress;
    private String schoolAddress;
    private String workAddress;
    private String image;

    public AccountDto(AccountDetail accountDetail) {
        this.id = accountDetail.getUserId();
        this.nickname = accountDetail.getNickname();
        this.email = accountDetail.getEmail();
        this.password = accountDetail.getPassword();
        this.name = accountDetail.getName();
        this.role = accountDetail.getRole();
        this.phone = accountDetail.getPhone();
        this.birthday = accountDetail.getBirthday();
        this.birthyear = accountDetail.getBirthyear();
        this.universityName = accountDetail.getUniversityName();
        this.shippingAddress = accountDetail.getShippingAddress();
        this.schoolAddress = accountDetail.getSchoolAddress();
        this.workAddress = accountDetail.getWorkAddress();
        this.image = accountDetail.getUserDetail().getImage();
    }

}
