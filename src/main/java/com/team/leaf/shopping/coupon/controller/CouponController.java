package com.team.leaf.shopping.coupon.controller;

import com.team.leaf.common.custom.LogIn;
import com.team.leaf.shopping.coupon.dto.CouponRequest;
import com.team.leaf.shopping.coupon.dto.CouponResponse;
import com.team.leaf.shopping.coupon.service.CouponService;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.exception.ApiResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/product/{productId}/coupon")
    public ApiResponse createCoupon(@PathVariable long productId, @LogIn AccountDetail accountDetail, @RequestBody CouponRequest request) {
        couponService.createCoupon(productId, accountDetail, request);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

    @GetMapping("/product/{productId}/coupon")
    public ApiResponse findCouponByProductId(@PathVariable long productId) {
        List<CouponResponse> result = couponService.findCouponByProductId(productId);

        return new ApiResponse(result);
    }

    @PostMapping("/product/{productId}/coupon/download/{couponId}")
    public ApiResponse downloadCoupon(@PathVariable long productId, @PathVariable long couponId, @LogIn AccountDetail accountDetail) {
        couponService.downloadCoupon(productId, couponId, accountDetail);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

}
