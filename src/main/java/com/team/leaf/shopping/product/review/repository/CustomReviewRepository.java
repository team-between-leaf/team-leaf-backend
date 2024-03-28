package com.team.leaf.shopping.product.review.repository;

import com.team.leaf.shopping.product.review.dto.ReviewResponse;
import com.team.leaf.shopping.product.review.entity.Review;

import java.util.List;

public interface CustomReviewRepository {

    List<ReviewResponse> findReviewByProductId(long productId);

    List<ReviewResponse> findReviewByUserId(long userId);

}
