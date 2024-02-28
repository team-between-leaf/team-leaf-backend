package com.team.leaf.shopping.product.review.entity;

import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    private int score;

    private String content;

    @CreationTimestamp
    private LocalDateTime reviewDate;

    @OneToOne
    private AccountDetail writer;

    @ManyToOne
    private Product product;

    private String orderType;


}
