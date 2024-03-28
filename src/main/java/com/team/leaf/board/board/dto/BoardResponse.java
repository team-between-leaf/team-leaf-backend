package com.team.leaf.board.board.dto;

import com.team.leaf.board.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Builder
@Component
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