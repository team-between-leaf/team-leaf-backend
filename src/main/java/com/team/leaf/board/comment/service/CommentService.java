package com.team.leaf.board.comment.service;

import com.team.leaf.board.board.dto.BoardResponse;
import com.team.leaf.board.board.entity.Board;
import com.team.leaf.board.board.repository.BoardRepository;
import com.team.leaf.board.board.service.BoardService;
import com.team.leaf.board.comment.dto.CommentRequest;
import com.team.leaf.board.comment.dto.CommentResponse;
import com.team.leaf.board.comment.entity.Comment;
import com.team.leaf.board.comment.repository.CommentRepository;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final AccountRepository accountRepository;

    private final BoardRepository boardRepository;

    public List<CommentResponse> getComment(Long boardId){
        return commentRepository.getAllComment(boardId);
    }

    @Transactional
    public String addComment(Long boardId, CommentRequest commentRequest){
        AccountDetail accountDetail = accountRepository.findByNickname(commentRequest.getNickname())
                .orElseThrow(()->new RuntimeException("Not Found Nickname"));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()->new RuntimeException("Not Found Board"));

        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .writer(accountDetail)
                .board(board)
                .build();
        commentRepository.save(comment);
        return "Success addComment";
    }

    @Transactional
    public String deleteComment(Long boardId, Long commentId){
        commentRepository.deleteByBoardBoardIdAndCommentId(boardId, commentId);
        return "Success deleteComment";
    }

    @Transactional
    public String updateComment(Long boardId, Long commentId, CommentRequest commentRequest){
        Comment comment = commentRepository.findByBoardBoardIdAndCommentId(boardId, commentId)
                .orElseThrow(()->new RuntimeException("Not Found Board"));
        comment.updateComment(commentRequest);
        return "Success updateComment";
    }

    public List<BoardResponse> findBoardsByCommentWriter(String nickname) {
        AccountDetail accountDetail = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Not Found Nickname"));
        List<Comment> comments = commentRepository.findByWriter(accountDetail);
        List<Board> boards = comments.stream()
                .map(comment -> comment.getBoard())
                .collect(Collectors.toList());
        return boards.stream()
                .map(board -> BoardService.convertToBoardResponse(board))
                .collect(Collectors.toList());
    }

}
