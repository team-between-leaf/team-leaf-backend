package com.team.leaf.alert.dto;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.team.leaf.user.account.entity.QAccountPrivacy.accountPrivacy;

@Getter
@RequiredArgsConstructor
public enum AlertTypeQuery {

    ORDER_DELIVERY(accountPrivacy.isFollowedSellerNotify.eq(true)),
    FOLLOWED_SELLER(accountPrivacy.isFollowedSellerNotify.eq(true)),
    MARKETING(accountPrivacy.isMarketingNotify.eq(true)),
    WISHLIST(accountPrivacy.isWishlistNotify.eq(true));

    private final BooleanExpression query;

}
