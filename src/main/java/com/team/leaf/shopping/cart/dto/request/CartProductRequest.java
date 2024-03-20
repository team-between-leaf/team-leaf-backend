package com.team.leaf.shopping.cart.dto.request;

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
public class CartProductRequest {

    private List<String> selectKeyData = new ArrayList<>();

    private List<String> selectValueData = new ArrayList<>();
}
