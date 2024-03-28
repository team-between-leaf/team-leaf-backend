package com.team.leaf.user.account.controller;

import com.team.leaf.board.board.dto.BoardResponse;
import com.team.leaf.board.board.service.BoardService;
import com.team.leaf.board.comment.service.CommentService;
import com.team.leaf.common.custom.LogIn;
import com.team.leaf.shopping.coupon.dto.CouponResponse;
import com.team.leaf.shopping.coupon.service.CouponService;
import com.team.leaf.shopping.product.review.dto.GetReviewRes;
import com.team.leaf.shopping.product.review.dto.ModifyReviewReq;
import com.team.leaf.shopping.product.review.dto.PostReviewReq;
import com.team.leaf.shopping.product.review.service.ReviewService;
import com.team.leaf.user.account.dto.common.ShippingAddressReq;
import com.team.leaf.user.account.dto.common.UpdateAccountReq;
import com.team.leaf.user.account.dto.response.UpdateAccountRes;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.service.AccountPageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/mypage")
public class AccountPageController {

    private final AccountPageService myPageService;
    private final ReviewService reviewService;
    private final CouponService couponService;
    private final BoardService boardService;
    private final CommentService commentService;

    @PatchMapping("/update")
    @Operation(summary = "회원 정보 수정 API")
    public ApiResponse<UpdateAccountRes> updateAccount(@LogIn @Parameter(hidden = true) AccountDetail accountDetail,
                                                        HttpServletResponse response, UpdateAccountReq accountDto) throws IOException {
        return new ApiResponse<>(myPageService.updateAccount(accountDetail, response, accountDto));
    }

    @PatchMapping("/address")
    @Operation(summary = "배송지 추가 및 수정 API")
    public ApiResponse<String> updateShippingAddress(@LogIn @Parameter(hidden = true) AccountDetail accountDetail,
                                                     @RequestBody ShippingAddressReq address) {
        return new ApiResponse<>(myPageService.updateShippingAddress(accountDetail, address));
    }

    @GetMapping("/address")
    @Operation(summary = "배송지 조회 API")
    public ApiResponse<List> getAllShippingAddress(@LogIn @Parameter(hidden = true) AccountDetail accountDetail) {
        return new ApiResponse<>(myPageService.getAllShippingAddress(accountDetail));
    }

    @DeleteMapping("/address/{addressId}")
    @Operation(summary = "배송지 삭제 API")
    public ApiResponse<String> deleteShippingAddress(@PathVariable(name = "addressId") Long addressId) {
        return new ApiResponse<>(myPageService.deleteShippingAddress(addressId));
    }

    @PostMapping("/review")
    @Operation(summary = "리뷰 작성 API")
    public ApiResponse<String> postReview(@LogIn @Parameter(hidden = true) AccountDetail accountDetail,
                                          @RequestBody PostReviewReq request) {
        return new ApiResponse<>(reviewService.postReview(accountDetail, request));
    }

    @PatchMapping("/review")
    @Operation(summary = "리뷰 수정 API")
    public ApiResponse<String> modifyReview(@LogIn @Parameter(hidden = true) AccountDetail accountDetail,
                                            @RequestBody ModifyReviewReq request) {
        return new ApiResponse<>(reviewService.modifyReview(accountDetail, request));
    }

    @DeleteMapping("/review/{reviewId}")
    @Operation(summary = "리뷰 삭제 API")
    public ApiResponse<String> deleteReview(@LogIn @Parameter(hidden = true) AccountDetail accountDetail,
                                            @PathVariable(name = "reviewId") Long reviewId) {
        return new ApiResponse<>(reviewService.deleteReview(accountDetail, reviewId));
    }

    @GetMapping("/review")
    @Operation(summary = "리뷰 조회 API")
    public ApiResponse<List<GetReviewRes>> getReviewById(@LogIn @Parameter(hidden = true) AccountDetail accountDetail) {
        return new ApiResponse<>(reviewService.getReviewsById(accountDetail));
    }

    @GetMapping("/coupon")
    @Operation(summary = "쿠폰 조회 API")
    public ApiResponse<List<CouponResponse>> getCouponById(@LogIn @Parameter(hidden = true) AccountDetail accountDetail) {
        return new ApiResponse<>(couponService.findCouponsByAccountUserId(accountDetail));
    }

    @GetMapping("/board")
    @Operation(summary = "내가 쓴 재능 요청 글 조회 API")
    public ApiResponse<List<BoardResponse>> getBoardsByUserId(@LogIn @Parameter(hidden = true) AccountDetail accountDetail) {
        return new ApiResponse<>(boardService.findBoardsByUserId(accountDetail));
    }

    @GetMapping("/board/comment")
    @Operation(summary = "댓글 단 재능 요청 글 조회 API")
    public ApiResponse<List<BoardResponse>> getBoardByCommentWriter(@LogIn @Parameter(hidden = true) AccountDetail accountDetail) {
        return new ApiResponse<>(commentService.findBoardsByCommentWriter(accountDetail.getNickname()));
    }

}

