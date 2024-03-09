package com.team.leaf.shopping.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomResponse {

    private long chatRoomId;

    private long sellerId;

    private String sellerNickName;

    private long buyerId;

    private String buyerNickName;

}
