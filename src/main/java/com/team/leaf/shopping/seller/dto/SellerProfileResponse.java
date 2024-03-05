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

    public SellerProfileResponse(double totalRating) {
        this.totalRating = totalRating;
    }

}
