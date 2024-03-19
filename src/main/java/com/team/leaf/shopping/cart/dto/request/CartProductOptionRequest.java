package com.team.leaf.shopping.cart.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductOptionRequest {

    private String selectKeyData;

    private String selectValueData;
}
