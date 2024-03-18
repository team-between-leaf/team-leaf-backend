package com.team.leaf.shopping.cart.repository;

import com.team.leaf.shopping.cart.entity.Cart;
import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.user.account.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findCartByUserAndProduct(AccountDetail user, Product product);

    Optional<List<Cart>> findAllByUser(AccountDetail accountDetail);
}
