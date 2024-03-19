package com.team.leaf.shopping.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductResponse {

    private List<CartProductOptionResponse> productOptionResponses = new ArrayList<>();

    private List<CartProductCouponResponse> productCouponResponses = new ArrayList<>();

    private List<CartProductSelectOptionResponse> productSelectOptionResponses = new ArrayList<>();

    private String title;

    private String image;

    private int price;

    private double discountRate;

    private int amount;

}
