package com.team.leaf.board.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {

    private long boardId;

    private int price;

    private String content;

    private String title;

    private String nickname;

    private LocalDateTime writeDate;
}