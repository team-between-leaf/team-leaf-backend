package com.team.leaf.shopping.product.review.entity;

import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.shopping.product.review.dto.KeywordRating;
import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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

    @Enumerated(EnumType.STRING)
    private KeywordRating deliveryRating;

    @Enumerated(EnumType.STRING)
    private KeywordRating packagingRating;

    @Enumerated(EnumType.STRING)
    private KeywordRating qualityRating;


    public void updateReview(int score, String content, KeywordRating deliveryRating, KeywordRating packagingRating, KeywordRating qualityRating) {
        this.score = score;
        this.content = content;
        this.deliveryRating = deliveryRating;
        this.packagingRating = packagingRating;
        this.qualityRating = qualityRating;
    }

}