package com.team.leaf.shopping.chat.controller;

import com.team.leaf.common.custom.LogIn;
import com.team.leaf.shopping.chat.dto.ChatDataResponse;
import com.team.leaf.shopping.chat.dto.ChatRoomResponse;
import com.team.leaf.shopping.chat.service.ChatService;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.exception.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/room/seller")
    @Operation(summary = "해당 사용자가 판매자 신분으로 거래중인 채팅 방 조회 [ 사용자 인증 정보 필요 ]")
    public ApiResponse findSellerChatRoomByUserId(@LogIn @Parameter(hidden = true) AccountDetail accountDetail) {
        List<ChatRoomResponse> result = chatService.findSellerChatRoomByUserId(accountDetail);

        return new ApiResponse(result);
    }

    @GetMapping("/room/buyer")
    @Operation(summary = "해당 사용자가 구매자 신분으로 거래중인 채팅 방 조회 [ 사용자 인증 정보 필요 ]")
    public ApiResponse findBuyerChatRoomByUserId(@LogIn @Parameter(hidden = true) AccountDetail accountDetail) {
        List<ChatRoomResponse> result = chatService.findBuyerChatRoomByUserId(accountDetail);

        return new ApiResponse(result);
    }

    @GetMapping("/room/{chatRoomId}")
    @Operation(summary = "특정 채팅 방의 채팅 데이터 가져오기")
    public ApiResponse findChatDataByChatRoomId(@PathVariable long chatRoomId) {
        List<ChatDataResponse> result = chatService.findChatDataByChatRoomId(chatRoomId);

        return new ApiResponse(result);
    }

    @GetMapping("/room/{sellerId}/{buyerId}")
    @Operation(summary = "구매자와 판매자 아이디로 채팅 데이터 가져오기")
    public ApiResponse findChatDataBySellerAndBuyer(@PathVariable long sellerId, @PathVariable long buyerId) {
        List<ChatDataResponse> result = chatService.findChatDataBySellerAndBuyer(sellerId, buyerId);

        return new ApiResponse(result);
    }

}