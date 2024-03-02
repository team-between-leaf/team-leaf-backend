package com.team.leaf.board.comment.repository;

import com.team.leaf.board.comment.dto.CommentResponse;

import java.util.List;

public interface CustomCommentRepository {
    List<CommentResponse> getAllComment(Long boardId);
}
