package com.team.leaf.shopping.cart.service;

import com.team.leaf.shopping.cart.entity.Cart;
import com.team.leaf.shopping.cart.repository.CartRepository;
import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.shopping.product.product.repository.ProductRepository;
import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Transactional
    public void addProductToCart(long productId, AccountDetail accountDetail) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("not found Product Data"));

        Cart cart = cartRepository.findCartByUserAndProduct(accountDetail, product)
                .orElseGet(() -> cartRepository.save(Cart.createCart(product, accountDetail)));

        cart.increaseAmount(1);
    }
}
