package com.team.leaf.shopping.product.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GetReviewRes {

    private long reviewId;

    private String nickname;

    private String content;

    private int score;

    private KeywordRating deliveryRating;

    private KeywordRating packagingRating;

    private KeywordRating qualityRating;

    private LocalDateTime createdDate;

}
