package com.team.leaf.shopping.wish.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishAddRequest {

    private long userId;

    private long productId;

    private int amount;
}
