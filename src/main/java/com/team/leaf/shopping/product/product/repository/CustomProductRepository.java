package com.team.leaf.shopping.product.product.repository;

import com.team.leaf.shopping.product.product.dto.ProductDetailResponse;
import com.team.leaf.shopping.product.product.dto.ProductRequest;
import com.team.leaf.shopping.product.product.dto.ProductResponse;
import com.team.leaf.shopping.product.product.entity.Product;
import com.team.leaf.user.account.entity.AccountDetail;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomProductRepository {

    List<ProductResponse> getAllProduct(Pageable pageable, ProductRequest request);

    List<ProductResponse> getAllProductBySearch(Pageable pageable, ProductRequest request, String search);

    Optional<ProductDetailResponse> findProductByProductId(long productId);

    Optional<Product> findProductByProductIdAndSeller(long productId, AccountDetail accountDetail);

}
