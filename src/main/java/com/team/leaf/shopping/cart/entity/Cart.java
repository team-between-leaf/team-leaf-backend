package com.team.leaf.shopping.cart.entity;

import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.user.account.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private int amount;

}
