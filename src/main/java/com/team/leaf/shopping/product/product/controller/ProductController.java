package com.team.leaf.shopping.product.product.controller;

import com.team.leaf.shopping.product.product.service.ProductService;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.exception.ApiResponseStatus;
import com.team.leaf.user.account.jwt.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product")
    public ApiResponse getAllProduct(Pageable pageable) {

        return new ApiResponse(productService.getAllProduct(pageable));
    }

    @PostMapping("/product/{productId}/wish-list")
    public ApiResponse addWishList(@AuthenticationPrincipal PrincipalDetails userDetails, @PathVariable long productId) {
        productService.addWishList(userDetails , productId);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

}
