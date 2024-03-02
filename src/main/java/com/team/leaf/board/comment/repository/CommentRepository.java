package com.team.leaf.board.comment.repository;

import com.team.leaf.board.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository {
    void deleteByBoardBoardIdAndCommentId(Long boardId, Long commentId);

    Optional<Comment> findByBoardBoardIdAndCommentId(Long boardId, Long commentId);
}
