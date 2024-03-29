package com.team.leaf.shopping.chat.entity;

import com.team.leaf.user.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue
    private long chatId;

    private String message;

    @CreationTimestamp
    private LocalDateTime writeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDetail writer;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    public static Chat createChat(AccountDetail accountDetail, String message) {
        return Chat.builder()
                .message(message)
                .writer(accountDetail)
                .build();
    }

}
