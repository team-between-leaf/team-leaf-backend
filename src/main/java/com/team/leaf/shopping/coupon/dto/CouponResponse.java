package com.team.leaf.shopping.coupon.dto;

import com.team.leaf.shopping.coupon.entity.DownloadCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CouponResponse {

    private long couponId;

    private String couponName;

    private double saleRate;

    private DownloadCondition downloadCondition;

}
