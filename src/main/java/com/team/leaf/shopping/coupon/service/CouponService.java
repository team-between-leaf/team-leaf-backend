package com.team.leaf.shopping.coupon.service;

import com.team.leaf.shopping.coupon.dto.CouponRequest;
import com.team.leaf.shopping.coupon.dto.CouponResponse;
import com.team.leaf.shopping.coupon.entity.Coupon;
import com.team.leaf.shopping.coupon.entity.DownloadCoupon;
import com.team.leaf.shopping.coupon.repository.CouponRepository;
import com.team.leaf.shopping.coupon.repository.DownloadCouponRepository;
import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.shopping.product.product.repository.ProductRepository;
import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final ProductRepository productRepository;
    private final DownloadCouponRepository downloadCouponRepository;

    public List<CouponResponse> findCouponByProductId(long productId) {
        return couponRepository.findCouponByProductId(productId);
    }

    @Transactional
    public void createCoupon(long productId, AccountDetail accountDetail, CouponRequest request) {
        Product product = productRepository.findProductByProductIdAndSeller(productId, accountDetail)
                .orElseThrow(() -> new RuntimeException("판매자만 쿠폰을 생성할 수 있습니다."));

        Coupon coupon = Coupon.createCoupon(request, product);
        product.addCoupon(coupon);
    }

    @Transactional
    public void downloadCoupon(long productId, long couponId, AccountDetail accountDetail) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("not Found Product"));

        Coupon coupon = couponRepository.findCouponByCouponIdAndProduct(couponId, product)
                .orElseThrow(() -> new RuntimeException("not Found Coupon"));

        DownloadCoupon downloadCoupon = DownloadCoupon.createDownloadCoupon(accountDetail, coupon);
        downloadCouponRepository.save(downloadCoupon);
    }

    public List<CouponResponse> findCouponByUserId(long userId) {
        return couponRepository.findCouponByUserId(userId);
    }
}
