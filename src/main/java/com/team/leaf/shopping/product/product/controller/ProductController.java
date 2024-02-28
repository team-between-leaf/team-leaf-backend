package com.team.leaf.shopping.product.product.controller;

import com.team.leaf.shopping.product.product.service.ProductService;
import com.team.leaf.user.account.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product")
    public ApiResponse getAllProduct(Pageable pageable) {

        return new ApiResponse(productService.getAllProduct(pageable));
    }

    @PostMapping("/product/wish-list")
    public ApiResponse addWishList() {
        
    }

}
