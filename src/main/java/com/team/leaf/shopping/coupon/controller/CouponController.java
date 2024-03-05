package com.team.leaf.shopping.coupon.controller;

import com.team.leaf.common.custom.LogIn;
import com.team.leaf.shopping.coupon.dto.CouponRequest;
import com.team.leaf.shopping.coupon.dto.CouponResponse;
import com.team.leaf.shopping.coupon.service.CouponService;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.exception.ApiResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/product/{productId}/coupon")
    @Operation(summary= "상품에 새로운 쿠폰 등록  [ 사용자 인증 정보 필요 ]")
    public ApiResponse createCoupon(@PathVariable long productId, @Parameter(hidden = true)  @LogIn AccountDetail accountDetail, @RequestBody CouponRequest request) {
        couponService.createCoupon(productId, accountDetail, request);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

    @GetMapping("/product/{productId}/coupon")
    @Operation(summary= "상품에 등록되어 있는 쿠폰 목록")
    public ApiResponse findCouponByProductId(@PathVariable long productId) {
        List<CouponResponse> result = couponService.findCouponByProductId(productId);

        return new ApiResponse(result);
    }

    @GetMapping("/product/coupon/{userId}")
    @Operation(summary = "특정 판매자가 등록한 쿠폰 목록")
    public ApiResponse findCouponByUserId(@PathVariable long userId) {
        List<CouponResponse> result = couponService.findCouponByUserId(userId);

        return new ApiResponse(result);
    }

    @PostMapping("/product/{productId}/coupon/download/{couponId}")
    @Operation(summary= "상품에 등록된 쿠폰 다운로드 API [ 사용자 인증 정보 필요 ] ")
    public ApiResponse downloadCoupon(@PathVariable long productId, @PathVariable long couponId, @Parameter(hidden = true) @LogIn AccountDetail accountDetail) {
        couponService.downloadCoupon(productId, couponId, accountDetail);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

}
