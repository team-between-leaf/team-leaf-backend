package com.team.leaf.shopping.cart.entity;

import com.team.leaf.shopping.cart.dto.request.CartProductRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductOption {

    @Id
    @GeneratedValue
    private long cartProductOptionId;

    private String selectKeyData;

    private String selectValueData;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;

    public void updateOption(String selectValueData) {
        this.selectValueData = selectValueData;
    }
}
