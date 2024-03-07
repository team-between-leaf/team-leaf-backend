package com.team.leaf.shopping.chat.entity;

import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue
    private long chatRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDetail seller;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDetail buyer;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<Chat> ChatData = new LinkedList<>();

}
