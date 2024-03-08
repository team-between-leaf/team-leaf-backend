package com.team.leaf.shopping.chat.repository;

import com.team.leaf.shopping.chat.dto.ChatDataResponse;
import com.team.leaf.shopping.chat.dto.ChatRoomResponse;
import com.team.leaf.shopping.chat.entity.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface CustomChatRoomRepository {

    List<ChatRoomResponse> findSellerChatRoomByUserId(long userId);

    List<ChatRoomResponse> findBuyerChatRoomByUserId(long userId);

    List<ChatDataResponse> findChatDataByChatRoomId(long chatRoomId);

    List<ChatDataResponse> findChatDataBySellerAndBuyer(long sellerUserId, long buyerUserId);

    Optional<ChatRoom> findChatRoomBySellerAndBuyer(long sellerUserId, long buyerUserId);

    Optional<ChatRoom> findChatRoomAndChatDataById(long chatRoomId);
}
