package com.team.leaf.shopping.order.entity;

import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.user.account.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    private int count;

    private int price;

    private String commission;

    private String status;

    private LocalDateTime orderDate;

    @OneToOne(cascade = CascadeType.PERSIST)
    private OrderDetail orderDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    private User order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

}
