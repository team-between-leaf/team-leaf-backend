package com.team.leaf.shopping.product.product.service;

import com.team.leaf.shopping.product.product.dto.ProductResponse;
import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.shopping.product.product.repository.ProductRepository;
import com.team.leaf.shopping.wish.entity.Wish;
import com.team.leaf.shopping.wish.repository.WishRepository;
import com.team.leaf.user.account.jwt.PrincipalDetails;
import com.team.leaf.user.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final WishRepository wishRepository;

    public List<ProductResponse> getAllProduct(Pageable pageable) {

        return productRepository.getAllProduct(pageable);
    }

    public void addWishList(PrincipalDetails detail,long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Not fount product"));

        wishRepository.save(Wish.createWish(detail.getAccountDetail(), product));
    }
}
