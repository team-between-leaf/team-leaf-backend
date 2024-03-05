package com.team.leaf.shopping.follow.controller;

import com.team.leaf.common.custom.LogIn;
import com.team.leaf.shopping.follow.dto.FollowRequest;
import com.team.leaf.shopping.follow.service.FollowService;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.exception.ApiResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping
    @Operation(summary = "팔로우 추가 API [ 사용자 인증 정보 필요 ] ")
    public ApiResponse addFollow(@LogIn @Parameter(hidden = true) AccountDetail accountDetail, @RequestBody FollowRequest request) {
        followService.addFollow(accountDetail, request);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

    @DeleteMapping
    @Operation(summary = "언팔로우 API [ 사용자 인증 정보 필요 ] ")
    public ApiResponse deleteFollow(@LogIn @Parameter(hidden = true) AccountDetail accountDetail, @RequestBody FollowRequest request) {
        followService.deleteFollow(accountDetail, request);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

}
