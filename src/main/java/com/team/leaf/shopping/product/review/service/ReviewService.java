package com.team.leaf.shopping.product.review.service;

import com.team.leaf.shopping.product.review.dto.ReviewResponse;
import com.team.leaf.shopping.product.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<ReviewResponse> findReviewByProductId(long productId) {
        return reviewRepository.findReviewByProductId(productId);
    }

    public List<ReviewResponse> findReviewByUserId(long userId) {
        return reviewRepository.findReviewByUserId(userId);
    }
}
