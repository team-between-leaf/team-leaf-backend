package com.team.leaf.shopping.product.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostReviewReq {
    private long productId;

    private String content;

    private int score;

    private KeywordRating deliveryRating;

    private KeywordRating packagingRating;

    private KeywordRating qualityRating;

}