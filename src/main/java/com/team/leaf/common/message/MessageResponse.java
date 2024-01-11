package com.team.leaf.common.message;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageResponse {

    private String message;

    public static MessageResponse createResponse(String message) {
        return MessageResponse.builder()
                .message(message)
                .build();
    }

}
