package com.team.leaf.user.account.entity;

import com.team.leaf.user.history.entity.SearchHistory;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
public class AccountDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String universityName;

    private String shippingAddress;

    private String schoolAddress;

    private String workAddress;

    private LocalDate joinDate;

    private String phone;

    private LocalDate lastAccess;

    private int loginFailCount;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private AccountPrivacy userDetail;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<SearchHistory> searchHistories;

}
