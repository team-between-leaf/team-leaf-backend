package com.team.leaf.shopping.chat.repository;

import com.team.leaf.shopping.chat.dto.ChatDataResponse;
import com.team.leaf.shopping.chat.dto.ChatRoomResponse;

import java.util.List;

public interface CustomChatRoomRepository {

    List<ChatRoomResponse> findSellerChatRoomByUserId(long userId);

    List<ChatRoomResponse> findBuyerChatRoomByUserId(long userId);

    List<ChatDataResponse> findChatDataByChatRoomId(long chatRoomId);

    List<ChatDataResponse> findChatDataBySellerAndBuyer(long sellerUserId, long buyerUserId);

}
