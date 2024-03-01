package com.team.leaf.board.board.service;

import com.team.leaf.board.board.dto.BoardRequest;
import com.team.leaf.board.board.dto.BoardResponse;
import com.team.leaf.board.board.entity.Board;
import com.team.leaf.board.board.repository.BoardRepository;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final AccountRepository accountRepository;
    public List<BoardResponse> boardPage(){
        return boardRepository.getAllBoard();
    }

    @Transactional
    public String addBoard(BoardRequest boardRequest){
        AccountDetail accountDetail = accountRepository.findByNickname(boardRequest.getNickname())
                .orElseThrow(()-> new RuntimeException("Not Found Nickname"));
        Board board = Board.builder()
                .price(boardRequest.getPrice())
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent())
                .writer(accountDetail)
                .build();
        boardRepository.save(board);
        return "Success addBoard";
    }

    @Transactional
    public String deleteBoard(Long boardId){
        boardRepository.deleteById(boardId);
        return "Success deleteBoard";
    }

    @Transactional
    public String updateBoard(Long boardId, BoardRequest boardRequest){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new RuntimeException("Not Found Board"));
        board.updateBoard(boardRequest);
        return "Success updateBoard";
    }
}