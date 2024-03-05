package com.team.leaf.shopping.seller.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.leaf.shopping.seller.dto.SellerNoticeRequest;
import com.team.leaf.shopping.seller.dto.SellerNoticeResponse;
import com.team.leaf.shopping.seller.dto.SellerProfileResponse;
import com.team.leaf.user.account.entity.QAccountDetail;
import com.team.leaf.user.account.entity.QAccountPrivacy;
import lombok.RequiredArgsConstructor;

import static com.team.leaf.shopping.seller.entity.QSellerNotice.sellerNotice;
import static com.team.leaf.shopping.follow.entity.QFollow.follow;
import static com.team.leaf.shopping.product.product.entity.QProduct.product;
import static com.team.leaf.shopping.product.review.entity.QReview.review;
import static com.team.leaf.user.account.entity.QAccountDetail.accountDetail;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class SellerRepositoryImpl implements CustomSellerRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public SellerProfileResponse findSellerInfoById(long userId) {
        QAccountDetail targetUser = new QAccountDetail("targetUser");
        QAccountDetail selfUser = new QAccountDetail("selfUser");

        Double reviewScore = jpaQueryFactory.select(review.score.avg())
                .from(product)
                .innerJoin(product.seller, accountDetail).on(accountDetail.userId.eq(userId))
                .leftJoin(product.reviews, review)
                .fetchOne();

        Long followedCount = jpaQueryFactory.select(selfUser.count())
                .from(follow)
                .innerJoin(follow.targetUser, targetUser).on(targetUser.userId.eq(userId))
                .leftJoin(follow.selfUser, selfUser)
                .fetchOne();

        return SellerProfileResponse.createResponse(reviewScore, followedCount);
    }

    @Override
    public List<SellerNoticeResponse> findSellerNoticeByUserId(long userId) {
        return jpaQueryFactory.select(Projections.constructor(
                        SellerNoticeResponse.class,
                        sellerNotice.sellerNoticeId,
                        sellerNotice.title,
                        sellerNotice.content,
                        accountDetail.nickname,
                        sellerNotice.writeDate
                ))
                .from(sellerNotice)
                .innerJoin(sellerNotice.writer, accountDetail)
                .fetch();
    }

    @Override
    public Optional<SellerNoticeResponse> findSellerNoticeByNoticeId(long noticeId) {
        SellerNoticeResponse result = jpaQueryFactory.select(Projections.constructor(
                        SellerNoticeResponse.class,
                        sellerNotice.sellerNoticeId,
                        sellerNotice.title,
                        sellerNotice.content,
                        accountDetail.nickname,
                        sellerNotice.writeDate
                ))
                .from(sellerNotice)
                .innerJoin(sellerNotice.writer, accountDetail)
                .where(sellerNotice.sellerNoticeId.eq(noticeId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
