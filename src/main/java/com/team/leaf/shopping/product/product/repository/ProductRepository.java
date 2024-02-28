package com.team.leaf.shopping.product.product.repository;

import com.team.leaf.shopping.product.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> , CustomProductRepository {

}
