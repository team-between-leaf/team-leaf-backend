package com.team.leaf.user.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountDetail accountDetail;

    @ManyToOne
    @JoinColumn(name = "interest_category_id")
    private InterestCategory interestCategory;

    public AccountInterest(AccountDetail accountDetail, InterestCategory interestCategory) {
        this.accountDetail = accountDetail;
        this.interestCategory = interestCategory;
    }

}

