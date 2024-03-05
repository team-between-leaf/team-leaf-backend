package com.team.leaf.shopping.seller.controller;

import com.team.leaf.common.custom.LogIn;
import com.team.leaf.shopping.seller.dto.SellerNoticeRequest;
import com.team.leaf.shopping.seller.dto.SellerNoticeResponse;
import com.team.leaf.shopping.seller.dto.SellerProfileResponse;
import com.team.leaf.shopping.seller.service.SellerService;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.exception.ApiResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;

    @GetMapping("/info/{userId}")
    @Operation(summary = "판매자 정보의 통계를 가져오는 API ( 총 평점, 팔로우 수 )")
    public ApiResponse getSellerInfoById(@PathVariable long userId) {
        SellerProfileResponse result = sellerService.getSellerInfoById(userId);

        return new ApiResponse(result);
    }

    @PostMapping("/notice")
    @Operation(summary = "판매자 공지사항 등록 API [ 사용자 인증 정보 필요 ] ")
    public ApiResponse addSellerNotice(@LogIn @Parameter(hidden = true) AccountDetail accountDetail, @RequestBody SellerNoticeRequest request) {
        sellerService.addSellerNotice(accountDetail, request);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

    @DeleteMapping("/notice/{noticeId}")
    @Operation(summary = "판매자 공지사항 삭제 API [ 사용자 인증 정보 필요 ] ")
    public ApiResponse deleteSellerNoticeByNoticeId(@LogIn @Parameter(hidden = true) AccountDetail accountDetail, @PathVariable long noticeId) {
        sellerService.deleteSellerNoticeByNoticeId(accountDetail, noticeId);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

    @GetMapping("/notice/{userId}")
    @Operation(summary = "특정 판매자의 공지사항 가져오는 API")
    public ApiResponse findSellerNoticeByUserId(@PathVariable long userId) {
        List<SellerNoticeResponse> result = sellerService.findSellerNoticeByUserId(userId);

        return new ApiResponse(result);
    }

    @GetMapping("/notice/detail/{noticeId}")
    @Operation(summary = "특정 공지사항 하나를 가져오는 API")
    public ApiResponse findSellerNoticeByNoticeId(@PathVariable long noticeId) {
        SellerNoticeResponse result = sellerService.findSellerNoticeByNoticeId(noticeId);

        return new ApiResponse(result);
    }

}
