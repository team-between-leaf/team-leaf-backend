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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final AccountRepository accountRepository;

    private final BoardResponse boardResponse;

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

    public List<BoardResponse> findBoardsByUserId(AccountDetail accountDetail) {
        List<Board> boards = boardRepository.findByWriter(accountDetail);
        return boards.stream()
                .map(board -> convertToBoardResponse(board))
                .collect(Collectors.toList());
    }

    public static BoardResponse convertToBoardResponse(Board board) {
        return BoardResponse.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .price(board.getPrice())
                .nickname(board.getWriter().getNickname())
                .writeDate(board.getWriteDate())
                .build();
    }

}