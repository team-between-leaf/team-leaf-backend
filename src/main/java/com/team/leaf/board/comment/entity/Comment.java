package com.team.leaf.board.comment.entity;

import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    private String content;

    @CreationTimestamp
    private LocalDateTime replyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDetail writer;

}
