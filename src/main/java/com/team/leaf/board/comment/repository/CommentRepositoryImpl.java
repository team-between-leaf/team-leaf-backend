package com.team.leaf.board.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.leaf.board.board.entity.Board;
import com.team.leaf.board.comment.dto.CommentResponse;
import com.team.leaf.board.comment.entity.QComment;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CustomCommentRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CommentResponse> getAllComment(Long boardId) {
        return jpaQueryFactory.select(Projections.constructor(CommentResponse.class,
                        QComment.comment.commentId,
                        QComment.comment.content,
                        QComment.comment.replyDate,
                        QComment.comment.writer.nickname))
                .from(QComment.comment)
                .where(QComment.comment.board.eq(Board.builder().boardId(boardId).build()))
                .fetch();
    }
}
