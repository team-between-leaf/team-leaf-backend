package com.team.leaf.shopping.coupon.dto;

import com.team.leaf.shopping.coupon.entity.Coupon;
import com.team.leaf.shopping.coupon.entity.DownloadCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CouponResponse {

    private long couponId;

    private long productId;

    private String couponName;

    private double saleRate;

    private DownloadCondition downloadCondition;

    public CouponResponse() {

    }

}
