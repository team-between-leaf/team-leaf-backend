package com.team.leaf.board.board.entity;

import com.team.leaf.board.comment.entity.Comment;
import com.team.leaf.user.account.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;

    private String title;

    private String content;

    private int price;

    @CreationTimestamp
    private LocalDateTime writeDate;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> comments;

}
