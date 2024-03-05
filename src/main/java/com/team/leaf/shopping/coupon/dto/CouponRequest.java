package com.team.leaf.shopping.coupon.dto;

import com.team.leaf.shopping.coupon.entity.DownloadCondition;
import lombok.Getter;

@Getter
public class CouponRequest {

    private String couponName;

    private double saleRate;

    private DownloadCondition downloadCondition;

}
