package com.team.leaf.shopping.product.product.repository;

import com.team.leaf.shopping.product.product.dto.ProductRequest;
import com.team.leaf.shopping.product.product.dto.ProductResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomProductRepository {

    List<ProductResponse> getAllProduct(Pageable pageable, ProductRequest request);

}
