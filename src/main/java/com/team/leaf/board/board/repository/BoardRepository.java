package com.team.leaf.board.board.repository;

import com.team.leaf.board.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository{

}