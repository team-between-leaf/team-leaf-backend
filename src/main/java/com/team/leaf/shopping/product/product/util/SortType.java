package com.team.leaf.shopping.product.product.util;

import com.querydsl.core.types.OrderSpecifier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.team.leaf.shopping.product.product.entity.QProduct.product;
import static com.team.leaf.shopping.product.review.entity.QReview.review;

@RequiredArgsConstructor
@Getter
public enum SortType {
    NORMAL(product.productId.desc() , "NORMAL"),    // 일반
    RATING(review.score.avg().desc() , "RATING"),     // 평점순
    SALE_RATE(product.saleRate.desc() , "SALE_RATE"), // 판매량
    VIEWS((product.views.desc()) , "VIEWS"),      // 조회수
    HIGH_PRICE(product.price.desc() , "HIGH_PRICE"),      // 가격순 높은 순
    LOW_PRICE(product.price.asc() , "LOW_PRICE");      // 가격 낮은 순

    private final OrderSpecifier sort;
    private final String sortType;

}
