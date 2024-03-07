package com.team.leaf.shopping.chat.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.leaf.shopping.chat.dto.ChatDataResponse;
import com.team.leaf.shopping.chat.dto.ChatRoomResponse;
import com.team.leaf.user.account.entity.QAccountDetail;
import lombok.RequiredArgsConstructor;

import static com.team.leaf.user.account.entity.QAccountDetail.accountDetail;
import static com.team.leaf.shopping.chat.entity.QChat.chat;
import static com.team.leaf.shopping.chat.entity.QChatRoom.chatRoom;

import java.util.List;

@RequiredArgsConstructor
public class ChatRepositoryImpl implements CustomChatRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<ChatRoomResponse> findSellerChatRoomByUserId(long userId) {
        QAccountDetail seller = new QAccountDetail("seller");
        QAccountDetail buyer = new QAccountDetail("buyer");

        return jpaQueryFactory.select(Projections.constructor(
                                ChatRoomResponse.class,
                                chatRoom.chatRoomId,
                                seller.userId,
                                seller.nickname,
                                buyer.userId,
                                buyer.nickname
                        )
                )
                .from(chatRoom)
                .innerJoin(chatRoom.seller, seller).on(seller.userId.eq(userId))
                .leftJoin(chatRoom.buyer, buyer)
                .fetch();
    }

    @Override
    public List<ChatRoomResponse> findBuyerChatRoomByUserId(long userId) {
        QAccountDetail seller = new QAccountDetail("seller");
        QAccountDetail buyer = new QAccountDetail("buyer");

        return jpaQueryFactory.select(Projections.constructor(
                                ChatRoomResponse.class,
                                chatRoom.chatRoomId,
                                seller.userId,
                                seller.nickname,
                                buyer.userId,
                                buyer.nickname
                        )
                )
                .from(chatRoom)
                .innerJoin(chatRoom.buyer, buyer).on(seller.userId.eq(userId))
                .leftJoin(chatRoom.seller, seller)
                .fetch();
    }

    @Override
    public List<ChatDataResponse> findChatDataByChatRoomId(long chatRoomId) {
        return jpaQueryFactory.select(Projections.constructor(
                                ChatDataResponse.class,
                                chat.message,
                                chat.writeTime,
                                accountDetail.nickname
                        )
                )
                .from(chatRoom)
                .innerJoin(chatRoom.ChatData, chat)
                .innerJoin(chat.writer, accountDetail)
                .where(chatRoom.chatRoomId.eq(chatRoomId))
                .fetch();
    }

    @Override
    public List<ChatDataResponse> findChatDataBySellerAndBuyer(long sellerUserId, long buyerUserId) {
        QAccountDetail seller = new QAccountDetail("seller");
        QAccountDetail buyer = new QAccountDetail("buyer");

        return jpaQueryFactory.select(Projections.constructor(
                                ChatDataResponse.class,
                                chat.message,
                                chat.writeTime,
                                accountDetail.nickname
                        )
                )
                .from(chatRoom)
                .innerJoin(chatRoom.seller, seller).on(seller.userId.eq(sellerUserId))
                .innerJoin(chatRoom.buyer, buyer).on(buyer.userId.eq(buyerUserId))
                .innerJoin(chatRoom.ChatData, chat)
                .innerJoin(chat.writer, accountDetail)
                .fetch();
    }

}
