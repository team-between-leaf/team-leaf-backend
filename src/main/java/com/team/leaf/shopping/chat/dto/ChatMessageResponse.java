package com.team.leaf.shopping.chat.dto;

import lombok.Builder;

@Builder
public class ChatMessageResponse {

    private String message;

    private String sendNickName;

    public static ChatMessageResponse createChatMessageResponse(String message, String sendNickName) {
        return ChatMessageResponse.builder()
                .message(message)
                .sendNickName(sendNickName)
                .build();
    }

}
