package com.team.leaf.shopping.product.review.controller;

import com.team.leaf.shopping.product.review.dto.ReviewResponse;
import com.team.leaf.shopping.product.review.service.ReviewService;
import com.team.leaf.user.account.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/product/review/{productId}")
    public ApiResponse findReviewByProductId(@PathVariable long productId) {
        List<ReviewResponse> result =  reviewService.findReviewByProductId(productId);

        return new ApiResponse(result);
    }

}
