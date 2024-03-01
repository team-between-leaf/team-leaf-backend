package com.team.leaf.shopping.product.product.controller;

import com.team.leaf.shopping.product.product.dto.ProductRequest;
import com.team.leaf.shopping.product.product.service.ProductService;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.exception.ApiResponseStatus;
import com.team.leaf.user.account.jwt.PrincipalDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product")
    @Operation(summary = "상품 데이터 가져오기")
    public ApiResponse getAllProduct(Pageable pageable, @RequestBody ProductRequest request) {

        return new ApiResponse(productService.getAllProduct(pageable, request));
    }

    @PostMapping("/product/{productId}/wish-list")
    @Operation(summary = "위시 리스트 추가")
    public ApiResponse addWishList(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable long productId) {
        productService.addWishList(authorizationHeader , productId);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

    @GetMapping("/product/{search}")
    @Operation(summary = "검색어를 통해 상품 데이터 가져오기")
    public ApiResponse getAllProductBySearch(Pageable pageable, @RequestBody ProductRequest request, @PathVariable String search) {

        return new ApiResponse(productService.getAllProductBySearch(pageable, request, search));
    }

}
