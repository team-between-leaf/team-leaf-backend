package com.team.leaf.shopping.seller.repository;

import com.team.leaf.user.account.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<AccountDetail, Long> {
}
