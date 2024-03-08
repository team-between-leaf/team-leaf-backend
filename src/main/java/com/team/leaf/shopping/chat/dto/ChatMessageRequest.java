package com.team.leaf.shopping.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatMessageRequest {

    private long chatRoomId;

    private String message;

    private long userId;

    private MessageType messageType;

}
