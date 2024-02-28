package com.team.leaf.shopping.product.product.service;

import com.team.leaf.shopping.product.product.dto.ProductResponse;
import com.team.leaf.shopping.product.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProduct(Pageable pageable) {

        return productRepository.getAllProduct(pageable);
    }
}
