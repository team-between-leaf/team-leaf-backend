package com.team.leaf.shopping.seller.controller;

import com.team.leaf.shopping.seller.dto.SellerProfileResponse;
import com.team.leaf.shopping.seller.service.SellerService;
import com.team.leaf.user.account.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;

    @GetMapping("/info/{userId}")
    public ApiResponse getSellerInfoById(@PathVariable long userId) {
        SellerProfileResponse result = sellerService.getSellerInfoById(userId);

        return new ApiResponse(result);
    }

}
