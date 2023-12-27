package com.team.leaf.shopping.wish.entity;

import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.user.account.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wishId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private int amount;

}
