package com.team.leaf.shopping.coupon.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.leaf.shopping.coupon.dto.CouponResponse;
import lombok.RequiredArgsConstructor;

import static com.team.leaf.user.account.entity.QAccountDetail.accountDetail;
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
                        product.productId,
                        coupon.couponName,
                        coupon.saleRate,
                        coupon.downloadCondition
                )).from(product)
                .innerJoin(product.coupons, coupon)
                .where(product.productId.eq(productId))
                .fetch();
    }

    @Override
    public List<CouponResponse> findCouponByUserId(long userId) {
        return jpaQueryFactory.select(Projections.constructor(
                        CouponResponse.class,
                        coupon.couponId,
                        product.productId,
                        coupon.couponName,
                        coupon.saleRate,
                        coupon.downloadCondition
                )).from(product)
                .innerJoin(product.coupons, coupon)
                .innerJoin(product.seller, accountDetail).on(accountDetail.userId.eq(userId))
                .fetch();
    }
}
