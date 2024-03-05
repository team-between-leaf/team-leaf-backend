package com.team.leaf.shopping.seller.service;

import com.team.leaf.shopping.seller.dto.SellerNoticeRequest;
import com.team.leaf.shopping.seller.dto.SellerNoticeResponse;
import com.team.leaf.shopping.seller.dto.SellerProfileResponse;
import com.team.leaf.shopping.seller.entity.SellerNotice;
import com.team.leaf.shopping.seller.repository.SellerNoticeRepository;
import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerNoticeRepository sellerRepository;

    public SellerProfileResponse getSellerInfoById(long userId) {

        return sellerRepository.findSellerInfoByUserId(userId);
    }

    @Transactional
    public void addSellerNotice(AccountDetail accountDetail, SellerNoticeRequest request) {
        SellerNotice result = SellerNotice.createSellerNotice(accountDetail, request);

        sellerRepository.save(result);
    }

    @Transactional
    public void deleteSellerNoticeByNoticeId(AccountDetail accountDetail, long noticeId) {
        long result = sellerRepository.deleteBySellerNoticeIdAndWriter(noticeId, accountDetail);

        if(result != 1) {
            throw new RuntimeException("작성자만 삭제할 수 있습니다.");
        }
    }

    public List<SellerNoticeResponse> findSellerNoticeByUserId(long userId) {
        return sellerRepository.findSellerNoticeByUserId(userId);
    }

    public SellerNoticeResponse findSellerNoticeByNoticeId(long noticeId) {
        return sellerRepository.findSellerNoticeByNoticeId(noticeId)
                .orElseThrow(() -> new RuntimeException("not found Data"));
    }

}
