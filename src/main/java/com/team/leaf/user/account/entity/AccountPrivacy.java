package com.team.leaf.user.account.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class AccountPrivacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userDetailId;

    private boolean isStudent;

    private String Image;

    private boolean isOrderDeliveryNotify;

    private boolean isFollowedSellerNotify;

    private boolean isMarketingNotify;

    private boolean isWishlistNotify;

    private LocalDate birthDate;

    public void updateOrderDeliveryNotify(boolean bool) {
        isOrderDeliveryNotify = bool;
    }

    public void updateFollowedSellerNotify(boolean bool) {
        isFollowedSellerNotify = bool;
    }

    public void updateMarketingNotify(boolean bool) {
        isMarketingNotify = bool;
    }

    public void updateWishlistNotify(boolean bool) {
        isWishlistNotify = bool;
    }

}
