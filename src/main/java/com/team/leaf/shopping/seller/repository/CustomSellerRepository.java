package com.team.leaf.shopping.seller.repository;

import com.team.leaf.shopping.seller.dto.SellerProfileResponse;

import java.util.Optional;

public interface CustomSellerRepository {

    SellerProfileResponse findSellerInfoById(long userId);

}
