package com.team.leaf.shopping.product.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOption {

    @Id
    @GeneratedValue
    private long productOptionId;

    private String keyData;

    private String valueData;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }
}
