package com.team.leaf.shopping.product.review.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.leaf.shopping.product.review.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;

import static com.team.leaf.user.account.entity.QAccountDetail.accountDetail;
import static com.team.leaf.shopping.product.review.entity.QReview.review;
import static com.team.leaf.shopping.product.product.entity.QProduct.product;

import java.util.List;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements CustomReviewRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReviewResponse> findReviewByProductId(long productId) {
        return jpaQueryFactory.select(Projections.constructor(ReviewResponse.class,
                        review.reviewId,
                        review.score,
                        review.content,
                        review.reviewDate,
                        accountDetail.nickname
                ))
                .from(review)
                .innerJoin(review.product, product).on(product.productId.eq(productId))
                .fetch();
    }
}
