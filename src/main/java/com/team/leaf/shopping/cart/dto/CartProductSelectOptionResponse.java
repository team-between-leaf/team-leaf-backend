package com.team.leaf.shopping.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductSelectOptionResponse {
    private String selectKeyData;

    private String selectValueData;
}
