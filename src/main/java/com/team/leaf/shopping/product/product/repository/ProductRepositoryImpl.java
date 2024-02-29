package com.team.leaf.shopping.product.product.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.leaf.shopping.product.product.dto.ProductRequest;
import com.team.leaf.shopping.product.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.team.leaf.shopping.product.product.entity.QProduct.product;
import static com.team.leaf.shopping.product.review.entity.QReview.review;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements CustomProductRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ProductResponse> getAllProduct(Pageable pageable, ProductRequest request) {
        return jpaQueryFactory.select(Projections.constructor(ProductResponse.class,
                        product.productId,
                        product.title,
                        product.description,
                        product.price,
                        product.image,
                        product.registrationDate,
                        product.saleRate,
                        product.views,
                        product.discountRate,
                        review.score.avg()
                ))
                .from(product)
                .leftJoin(product.reviews, review).on(review.product.eq(product))
                .groupBy(product.productId)
                .orderBy(request.getSortType().getSort())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<ProductResponse> getAllProductBySearch(Pageable pageable, ProductRequest request, String search) {
        return jpaQueryFactory.select(Projections.constructor(ProductResponse.class,
                        product.productId,
                        product.title,
                        product.description,
                        product.price,
                        product.image,
                        product.registrationDate,
                        product.saleRate,
                        product.views,
                        product.discountRate,
                        review.score.avg()
                ))
                .from(product)
                .leftJoin(product.reviews, review).on(review.product.eq(product))
                .groupBy(product.productId)
                .orderBy(request.getSortType().getSort())
                .where(product.title.contains(search))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
