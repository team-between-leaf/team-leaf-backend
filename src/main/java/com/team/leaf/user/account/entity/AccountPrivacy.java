package com.team.leaf.user.account.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountPrivacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userDetailId;

    private boolean isStudent;

    private String sellerIntroduce;

    private String Image;

    private boolean isOrderDeliveryNotify;

    private boolean isFollowedSellerNotify;

    private boolean isMarketingNotify;

    private boolean isWishlistNotify;

    private LocalDate birthDate;

    @OneToOne(mappedBy = "userDetail", fetch = FetchType.LAZY)
    private AccountDetail accountDetail;

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

    public static AccountPrivacy createAccountPrivacy() {
        return AccountPrivacy.builder()
                .isStudent(false)
                .isFollowedSellerNotify(false)
                .isMarketingNotify(false)
                .isOrderDeliveryNotify(false)
                .isWishlistNotify(false)
                .build();
    }

    public void updateProfileImage(String url) {
        this.Image = url;
    }

}
