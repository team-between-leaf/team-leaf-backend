package com.team.leaf.shopping.chat.service;

import com.team.leaf.shopping.chat.dto.ChatDataResponse;
import com.team.leaf.shopping.chat.dto.ChatRoomResponse;
import com.team.leaf.shopping.chat.repository.ChatRoomRepository;
import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRepository;

    public List<ChatRoomResponse> findSellerChatRoomByUserId(AccountDetail accountDetail) {
        return chatRepository.findSellerChatRoomByUserId(accountDetail.getUserId());
    }

    public List<ChatRoomResponse> findBuyerChatRoomByUserId(AccountDetail accountDetail) {
        return chatRepository.findBuyerChatRoomByUserId(accountDetail.getUserId());
    }

    public List<ChatDataResponse> findChatDataByChatRoomId(long chatRoomId) {
        return chatRepository.findChatDataByChatRoomId(chatRoomId);
    }

    public List<ChatDataResponse> findChatDataBySellerAndBuyer(long sellerUserId, long buyerUserId) {
        List<ChatDataResponse> result = chatRepository.findChatDataBySellerAndBuyer(sellerUserId, buyerUserId);

        createChatRoomIfNoChatRoom(sellerUserId, buyerUserId);

        return result;
    }

    @Transactional
    private void createChatRoomIfNoChatRoom(long sellerUserId, long buyerUserId) {

    }

}
