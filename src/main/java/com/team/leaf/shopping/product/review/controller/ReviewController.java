package com.team.leaf.shopping.product.review.controller;

import com.team.leaf.shopping.product.review.dto.ReviewResponse;
import com.team.leaf.shopping.product.review.service.ReviewService;
import com.team.leaf.user.account.exception.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary= "특정 상품에 등록된 리뷰 가져오기 API")
    public ApiResponse findReviewByProductId(@PathVariable long productId) {
        List<ReviewResponse> result =  reviewService.findReviewByProductId(productId);

        return new ApiResponse(result);
    }

}
