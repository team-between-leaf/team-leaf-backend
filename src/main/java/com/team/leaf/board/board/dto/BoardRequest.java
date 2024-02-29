package com.team.leaf.board.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequest {

    private String nickname;

    private String title;

    private int price;

    private String content;

}