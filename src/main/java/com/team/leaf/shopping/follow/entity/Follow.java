package com.team.leaf.shopping.follow.entity;

import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follow {

    @Id
    @GeneratedValue
    private long followId;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDetail follower;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDetail following;

}
