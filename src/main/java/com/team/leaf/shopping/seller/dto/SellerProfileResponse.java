package com.team.leaf.shopping.seller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class SellerProfileResponse {

    private double totalRating;

    @Setter
    private long followUser;

    public static SellerProfileResponse createResponse(Double totalRating, Long followUser) {
        if(totalRating == null) {
            totalRating = 0.0;
        }

        if(followUser == null) {
            followUser = 0L;
        }

        return new SellerProfileResponse(totalRating, followUser);
    }

}
