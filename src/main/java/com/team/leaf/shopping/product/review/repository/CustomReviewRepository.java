package com.team.leaf.shopping.product.review.repository;

import com.team.leaf.shopping.product.review.dto.ReviewResponse;

import java.util.List;

public interface CustomReviewRepository {

    List<ReviewResponse> findReviewByProductId(long productId);

}
