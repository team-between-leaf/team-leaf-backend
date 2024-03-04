package com.team.leaf.shopping.cart.repository;

import com.team.leaf.shopping.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
