package com.team.leaf.shopping.seller.repository;

import com.team.leaf.shopping.seller.entity.SellerNotice;
import com.team.leaf.user.account.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerNoticeRepository extends JpaRepository<SellerNotice, Long> , CustomSellerNoticeRepository {

    long deleteBySellerNoticeIdAndWriter(long noticeId, AccountDetail writer);

}
