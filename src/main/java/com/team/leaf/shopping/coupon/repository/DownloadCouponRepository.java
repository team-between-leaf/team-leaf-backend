package com.team.leaf.shopping.coupon.repository;

import com.team.leaf.shopping.coupon.entity.DownloadCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DownloadCouponRepository extends JpaRepository<DownloadCoupon, Long> {
    List<DownloadCoupon> findByAccountDetailUserId(long userId);
}
