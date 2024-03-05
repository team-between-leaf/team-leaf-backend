package com.team.leaf.shopping.seller.repository;

import com.team.leaf.shopping.seller.dto.SellerNoticeResponse;
import com.team.leaf.shopping.seller.dto.SellerProfileResponse;

import java.util.List;
import java.util.Optional;

public interface CustomSellerRepository {

    SellerProfileResponse findSellerInfoById(long userId);

    List<SellerNoticeResponse> findSellerNoticeByUserId(long userId);

    Optional<SellerNoticeResponse> findSellerNoticeByNoticeId(long noticeId);

}
