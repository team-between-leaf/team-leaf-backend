package com.team.leaf.board.board.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.leaf.board.board.dto.BoardResponse;
import com.team.leaf.board.board.entity.QBoard;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements CustomBoardRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<BoardResponse> getAllBoard() {
        return jpaQueryFactory.select(Projections.constructor(BoardResponse.class,
                        QBoard.board.boardId,
                        QBoard.board.price,
                        QBoard.board.content,
                        QBoard.board.title,
                        QBoard.board.writer.nickname,
                        QBoard.board.writeDate))
                .from(QBoard.board)
                .fetch();
    }
}