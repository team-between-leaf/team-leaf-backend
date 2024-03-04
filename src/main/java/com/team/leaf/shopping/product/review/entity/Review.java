package com.team.leaf.shopping.product.review.entity;

import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    private int score;

    private String content;

    @CreationTimestamp
    private LocalDateTime reviewDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDetail writer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private String orderType;

}