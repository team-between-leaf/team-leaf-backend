package com.team.leaf.board.comment.controller;

import com.team.leaf.board.comment.dto.CommentRequest;
import com.team.leaf.board.comment.dto.CommentResponse;
import com.team.leaf.board.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/board/{boardId}/comment")
    public ResponseEntity<List<CommentResponse>> getComment(@PathVariable(name="boardId") Long boardId){
        return ResponseEntity.ok(commentService.getComment(boardId));
    }

    @PostMapping("/board/{boardId}/comment")
    public ResponseEntity addComment(@PathVariable(name = "boardId") Long boardId,
                                     @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.addComment(boardId, commentRequest));
    }

    @DeleteMapping("/board/{boardId}/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable(name = "boardId") Long boardId,
                                        @PathVariable(name = "commentId") Long commentId){
        return ResponseEntity.ok(commentService.deleteComment(boardId, commentId));
    }

    @PatchMapping("/board/{boardId}/comment/{commentId}")
    public ResponseEntity updateComment(@PathVariable(name = "boardId") Long boardId,
                                        @PathVariable(name = "commentId") Long commentId,
                                        @RequestBody CommentRequest commentRequest){
        return ResponseEntity.ok(commentService.updateComment(boardId, commentId, commentRequest));
    }

}
