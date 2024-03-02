package com.team.leaf.board.comment.entity;

import com.team.leaf.board.board.entity.Board;
import com.team.leaf.board.comment.dto.CommentRequest;
import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    private String content;

    @CreationTimestamp
    private LocalDateTime replyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDetail writer;

    public void updateComment(CommentRequest commentRequest){
        this.content = commentRequest.getContent();
    }
}
