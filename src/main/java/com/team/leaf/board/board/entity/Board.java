package com.team.leaf.board.board.entity;

import com.team.leaf.board.board.dto.BoardRequest;
import com.team.leaf.board.comment.entity.Comment;
import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private AccountDetail writer;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> comments;

    public void updateBoard(BoardRequest boardRequest){
        this.title = boardRequest.getTitle();
        this.content = boardRequest.getContent();
        this.price = boardRequest.getPrice();
    }
}
