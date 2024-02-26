package com.team.leaf.shopping.wish.entity;

import com.team.leaf.shopping.product.product.entity.Product;
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
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wishId;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDetail user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private int amount;

}
