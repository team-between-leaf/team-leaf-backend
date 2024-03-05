package com.team.leaf.shopping.seller.service;

import com.team.leaf.shopping.seller.dto.SellerProfileResponse;
import com.team.leaf.shopping.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    public SellerProfileResponse getSellerInfoById(long userId) {


    return null;
    }
}
