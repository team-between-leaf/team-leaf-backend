package com.team.leaf.shopping.product.product.service;

import com.team.leaf.shopping.product.product.dto.ProductDetailResponse;
import com.team.leaf.shopping.product.product.dto.ProductRequest;
import com.team.leaf.shopping.product.product.dto.ProductResponse;
import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.shopping.product.product.repository.ProductRepository;
import com.team.leaf.shopping.wish.entity.Wish;
import com.team.leaf.shopping.wish.repository.WishRepository;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.exception.ApiResponseStatus;
import com.team.leaf.user.account.jwt.JwtTokenUtil;
import com.team.leaf.user.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final WishRepository wishRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final AccountRepository accountRepository;

    public List<ProductResponse> getAllProduct(Pageable pageable, ProductRequest request) {

        return productRepository.getAllProduct(pageable, request);
    }

    public void addWishList(String token,long productId) {
        String email = jwtTokenUtil.getEmailFromToken(token);
        AccountDetail account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("not fount User"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Not fount product"));

        wishRepository.save(Wish.createWish(account , product));
    }

    public List<ProductResponse> getAllProductBySearch(Pageable pageable, ProductRequest request, String search) {

        return productRepository.getAllProductBySearch(pageable, request, search);
    }

    public ProductDetailResponse findProductByProductId(long productId) {
        return productRepository.findProductByProductId(productId)
                .orElseThrow(() -> new RuntimeException("not Fount Data"));
    }

    public List<ProductResponse> findSellerProductByUserId(Pageable pageable, ProductRequest request, long userId) {

        return productRepository.findSellerProductByUserId(pageable, request, userId);
    }
}
