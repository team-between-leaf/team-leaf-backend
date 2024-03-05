package com.team.leaf.shopping.coupon.repository;

import com.team.leaf.shopping.coupon.dto.CouponResponse;

import java.util.List;

public interface CustomCouponRepository {

    List<CouponResponse> findCouponByProductId(long productId);

    List<CouponResponse> findCouponByUserId(long userId);

}
