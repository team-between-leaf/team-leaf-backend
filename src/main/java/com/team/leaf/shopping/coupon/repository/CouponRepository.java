package com.team.leaf.shopping.coupon.repository;

import com.team.leaf.shopping.coupon.entity.Coupon;
import com.team.leaf.shopping.product.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> , CustomCouponRepository{

    Optional<Coupon> findCouponByCouponIdAndProduct(long couponId, Product product);

}
