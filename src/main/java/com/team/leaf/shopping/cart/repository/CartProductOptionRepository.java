package com.team.leaf.shopping.cart.repository;

import com.team.leaf.shopping.cart.entity.Cart;
import com.team.leaf.shopping.cart.entity.CartProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartProductOptionRepository extends JpaRepository<CartProductOption, Long> {

    Optional<List<CartProductOption>> findAllByCart(Cart cart);
}
