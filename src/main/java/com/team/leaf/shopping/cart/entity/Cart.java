package com.team.leaf.shopping.cart.entity;

import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDetail user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartProductOption> cartProductOption;

    @Builder.Default
    private int amount = 0;

    public void increaseAmount(int amount) {
        this.amount += amount;
    }

    public static Cart createCart(Product product, AccountDetail accountDetail) {
        return Cart.builder()
                .user(accountDetail)
                .product(product)
                .build();
    }

}
