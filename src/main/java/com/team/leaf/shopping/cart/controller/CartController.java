package com.team.leaf.shopping.cart.controller;

import com.team.leaf.common.custom.LogIn;
import com.team.leaf.shopping.cart.service.CartService;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.exception.ApiResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/{productId}")
    @Operation(summary= "상품을 장바구니에 추가 API")
    public ApiResponse addProductToCart(@PathVariable long productId, @LogIn AccountDetail accountDetail) {
        cartService.addProductToCart(productId, accountDetail);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

}
