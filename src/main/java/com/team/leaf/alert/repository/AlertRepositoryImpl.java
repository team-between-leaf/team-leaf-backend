package com.team.leaf.alert.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.leaf.alert.dto.AlertTypeQuery;
import lombok.RequiredArgsConstructor;

import static com.team.leaf.user.account.entity.QAccountDetail.accountDetail;
import static com.team.leaf.user.account.entity.QAccountPrivacy.accountPrivacy;
import java.util.List;

@RequiredArgsConstructor
public class AlertRepositoryImpl implements CustomAlertRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Long> findAccountIdByNoticeAgreedTo(AlertTypeQuery query) {
        return jpaQueryFactory.select(accountDetail.userId)
                .from(accountDetail)
                .innerJoin(accountDetail.userDetail, accountPrivacy).on(query.getQuery())
                .fetch();
    }
}
