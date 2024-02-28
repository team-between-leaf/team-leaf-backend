package com.team.leaf.shopping.product.product.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.leaf.shopping.product.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import static com.team.leaf.shopping.product.review.entity.QReview.review;
import static com.team.leaf.shopping.product.product.entity.QProduct.product;

import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements CustomProductRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ProductResponse> getAllProduct(Pageable pageable) {

        return jpaQueryFactory.select(Projections.constructor(ProductResponse.class,
                        product.productId,
                        product.title,
                        product.description,
                        product.price,
                        product.image,
                        product.registrationDate,
                        product.views,
                        product.discountRate,
                        review.score.avg()
                ))
                .from(product)
                .groupBy(product.reviews , review)
                .orderBy(product.productId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
