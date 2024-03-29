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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "chatRoom")
    @Builder.Default
    private List<Chat> chatData = new LinkedList<>();

    public void addChatData(Chat chat) {
        chatData.add(chat);
        chat.setChatRoom(this);
    }

    public static ChatRoom createChatRoom(AccountDetail seller, AccountDetail buyer) {
        return ChatRoom.builder()
                .buyer(buyer)
                .seller(seller)
                .build();
    }

}
