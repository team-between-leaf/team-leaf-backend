package com.team.leaf.shopping.history.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.Ignore;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private AccountDetail accountDetail;

    private String content;

    public static History createHistory(String content, AccountDetail accountDetail) {
        return History.builder()
                .accountDetail(accountDetail)
                .content(content)
                .build();
    }

}
