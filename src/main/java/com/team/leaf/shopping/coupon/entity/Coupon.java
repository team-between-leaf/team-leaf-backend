package com.team.leaf.shopping.coupon.entity;

import com.team.leaf.shopping.coupon.dto.CouponRequest;
import com.team.leaf.shopping.product.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue
    private long couponId;

    private String couponName;

    private double saleRate;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private DownloadCondition downloadCondition;

    public static Coupon createCoupon(CouponRequest request, Product product) {
        return Coupon.builder()
                .couponName(request.getCouponName())
                .saleRate(request.getSaleRate())
                .product(product)
                .downloadCondition(request.getDownloadCondition())
                .build();
    }
}
