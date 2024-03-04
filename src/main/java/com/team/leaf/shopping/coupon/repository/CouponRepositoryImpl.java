package com.team.leaf.shopping.coupon.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.leaf.shopping.coupon.dto.CouponResponse;
import lombok.RequiredArgsConstructor;

import static com.team.leaf.shopping.product.product.entity.QProduct.product;
import static com.team.leaf.shopping.coupon.entity.QCoupon.coupon;

import java.util.List;

@RequiredArgsConstructor
public class CouponRepositoryImpl implements CustomCouponRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CouponResponse> findCouponByProductId(long productId) {

        return jpaQueryFactory.select(Projections.constructor(
                        CouponResponse.class,
                        coupon.couponId,
                        coupon.couponName,
                        coupon.saleRate,
                        coupon.saleRate
                )).from(product)
                .innerJoin(product.coupons, coupon)
                .where(product.productId.eq(productId))
                .fetch();
    }
}
