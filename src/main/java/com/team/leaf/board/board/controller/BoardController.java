package com.team.leaf.board.board.controller;

import com.team.leaf.board.board.dto.BoardRequest;
import com.team.leaf.board.board.dto.BoardResponse;
import com.team.leaf.board.board.service.BoardService;
import com.team.leaf.user.account.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board")
    public ResponseEntity<List<BoardResponse>> boardPage(){
        return ResponseEntity.ok(boardService.boardPage());
    }

    @PostMapping("/board")
    public ResponseEntity addBoard(@RequestBody BoardRequest boardRequest){
        return ResponseEntity.ok(boardService.addBoard(boardRequest));
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable(name = "boardId") Long boardId){
        return ResponseEntity.ok(boardService.deleteBoard(boardId));
    }

    @PatchMapping("/board/{boardId}")
    public ResponseEntity updateBoard(@PathVariable(name="boardId") Long boardId,
                                      @RequestBody BoardRequest boardRequest){
        //boardService.updateBoard(boardId,boardRequest)
        return ResponseEntity.ok(boardService.updateBoard(boardId,boardRequest));
    }
}