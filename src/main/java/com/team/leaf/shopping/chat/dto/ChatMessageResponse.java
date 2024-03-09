package com.team.leaf.shopping.chat.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.team.leaf.user.account.entity.AccountDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
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
