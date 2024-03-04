package com.team.leaf.shopping.coupon.entity;

import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownloadCoupon {

    @Id
    @GeneratedValue
    private long downloadCouponId;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDetail accountDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

    public static DownloadCoupon createDownloadCoupon(AccountDetail accountDetail, Coupon coupon) {
        return DownloadCoupon.builder()
                .accountDetail(accountDetail)
                .coupon(coupon)
                .build();
    }

}
