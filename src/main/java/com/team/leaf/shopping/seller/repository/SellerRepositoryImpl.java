package com.team.leaf.shopping.seller.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.leaf.shopping.seller.dto.SellerProfileResponse;
import com.team.leaf.user.account.entity.QAccountDetail;
import com.team.leaf.user.account.entity.QAccountPrivacy;
import lombok.RequiredArgsConstructor;

import static com.team.leaf.shopping.follow.entity.QFollow.follow;
import static com.team.leaf.shopping.product.product.entity.QProduct.product;
import static com.team.leaf.shopping.product.review.entity.QReview.review;
import static com.team.leaf.user.account.entity.QAccountDetail.accountDetail;

import java.util.Optional;

@RequiredArgsConstructor
public class SellerRepositoryImpl implements CustomSellerRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<SellerProfileResponse> findSellerInfoById(long userId) {
        QAccountDetail followingUser = new QAccountDetail("followingUser");
        QAccountDetail followerUser = new QAccountDetail("followerUser");

        SellerProfileResponse result = jpaQueryFactory.select(
                        Projections.constructor(SellerProfileResponse.class,
                                review.score.avg()
                        )
                ).from(product)
                .innerJoin(product.seller, accountDetail).on(accountDetail.userId.eq(userId))
                .leftJoin(product.reviews, review)
                .groupBy(product.productId)
                .fetchOne();

        long followedCount = jpaQueryFactory.select(followerUser.count())
                .from(follow)
                .innerJoin(follow.following, followingUser).on(followingUser.userId.eq(userId))
                .leftJoin(follow.follower, followerUser)
                .fetchOne();

        result.setFollowUser(followedCount);

        return Optional.ofNullable(result);
    }
}
