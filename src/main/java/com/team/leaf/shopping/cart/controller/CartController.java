package com.team.leaf.shopping.cart.controller;

import com.team.leaf.common.custom.LogIn;
import com.team.leaf.shopping.cart.dto.request.CartProductOptionRequest;
import com.team.leaf.shopping.cart.dto.request.CartProductRequest;
import com.team.leaf.shopping.cart.service.CartService;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.exception.ApiResponseStatus;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/{productId}")
    @Operation(summary= "상품을 장바구니에 추가 API [ 사용자 인증 정보 필요 ] ")
    public ApiResponse addProductToCart(@PathVariable(name = "productId") long productId,
                                        @RequestBody CartProductRequest cartProductRequest,
                                        @Parameter(hidden = true) @LogIn AccountDetail accountDetail) {
        cartService.addProductToCart(productId,cartProductRequest, accountDetail);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

    @GetMapping("/cart")
    public ApiResponse getCart(@LogIn AccountDetail accountDetail){
        return new ApiResponse(cartService.getCart(accountDetail));
    }

    @PatchMapping("/cart/{productId}")
    public ApiResponse updateCart(@PathVariable(name = "productId") Long productId,
                                  @RequestBody CartProductOptionRequest cartProductOptionRequest,
                                  @LogIn AccountDetail accountDetail){
        return new ApiResponse(cartService.updateCartOption(productId, cartProductOptionRequest));
    }

    @DeleteMapping("/cart")
    public ApiResponse deleteCart(@RequestParam("productId") List<Long> productIds, @LogIn AccountDetail accountDetail){
        return new ApiResponse(cartService.deleteCart(productIds, accountDetail));
    }

}
