package com.team.leaf.shopping.product.review.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.leaf.shopping.product.review.dto.ReviewResponse;
import com.team.leaf.user.account.entity.QAccountDetail;
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
                        product.productId,
                        review.score,
                        review.content,
                        review.reviewDate,
                        accountDetail.nickname
                ))
                .from(review)
                .innerJoin(review.product, product).on(product.productId.eq(productId))
                .innerJoin(review.writer, accountDetail)
                .fetch();
    }

    @Override
    public List<ReviewResponse> findReviewByUserId(long userId) {
        QAccountDetail accountDetail2 = new QAccountDetail("account2");

        return jpaQueryFactory.select(Projections.constructor(ReviewResponse.class,
                        review.reviewId,
                        product.productId,
                        review.score,
                        review.content,
                        review.reviewDate,
                        accountDetail2.nickname
                ))
                .from(review)
                .innerJoin(review.product, product)
                .innerJoin(product.seller, accountDetail).on(accountDetail.userId.eq(userId))
                .innerJoin(review.writer, accountDetail2)
                .fetch();
    }
}
