package com.team.leaf.shopping.chat.dto;

import com.team.leaf.user.account.entity.AccountDetail;
import lombok.Builder;

@Builder
public class ChatMessageResponse {

    private String message;

    private String sendNickName;

    private long senderId;

    public static ChatMessageResponse createChatMessageResponse(String message, AccountDetail accountDetail) {
        return ChatMessageResponse.builder()
                .message(message)
                .sendNickName(accountDetail.getNickname())
                .senderId(accountDetail.getUserId())
                .build();
    }

}
