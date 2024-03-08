package com.team.leaf.shopping.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ChatDataResponse {

    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:MM:SS" , timezone = "Asia/Seoul")
    private LocalDateTime writeTime;

    private String writerNickName;

}
