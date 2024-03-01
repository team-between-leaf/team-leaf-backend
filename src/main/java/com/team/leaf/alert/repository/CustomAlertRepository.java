package com.team.leaf.alert.repository;

import com.team.leaf.alert.dto.AlertTypeQuery;

import java.util.List;

public interface CustomAlertRepository {

    List<Long> findAccountIdByNoticeAgreedTo(AlertTypeQuery query);

}
