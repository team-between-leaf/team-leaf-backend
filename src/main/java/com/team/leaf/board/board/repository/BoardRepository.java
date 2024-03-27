package com.team.leaf.board.board.repository;

import com.team.leaf.board.board.entity.Board;
import com.team.leaf.user.account.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository{

    //List<Board> findByWriterAccountDetailUserId(long userId);

    List<Board> findByWriter(AccountDetail accountDetail);
}