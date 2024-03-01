package com.team.leaf.board.board.repository;

import com.team.leaf.board.board.dto.BoardResponse;

import java.util.List;

public interface CustomBoardRepository {
    List<BoardResponse> getAllBoard();

}
