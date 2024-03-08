package com.team.leaf.shopping.chat.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.leaf.shopping.chat.dto.ChatDataResponse;
import com.team.leaf.shopping.chat.dto.ChatRoomResponse;
import com.team.leaf.shopping.chat.entity.ChatRoom;
import com.team.leaf.user.account.entity.QAccountDetail;
import lombok.RequiredArgsConstructor;

import static com.team.leaf.user.account.entity.QAccountDetail.accountDetail;
import static com.team.leaf.shopping.chat.entity.QChat.chat;
import static com.team.leaf.shopping.chat.entity.QChatRoom.chatRoom;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements CustomChatRoomRepository {

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
                .innerJoin(chatRoom.buyer, buyer)
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
                .innerJoin(chatRoom.buyer, buyer).on(buyer.userId.eq(userId))
                .innerJoin(chatRoom.seller, seller)
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
                .innerJoin(chatRoom.chatData, chat)
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
                .innerJoin(chatRoom.chatData, chat)
                .innerJoin(chat.writer, accountDetail)
                .fetch();
    }

    @Override
    public Optional<ChatRoom> findChatRoomBySellerAndBuyer(long sellerUserId, long buyerUserId) {
        QAccountDetail seller = new QAccountDetail("seller");
        QAccountDetail buyer = new QAccountDetail("buyer");

        ChatRoom result = jpaQueryFactory.select(chatRoom)
                .from(chatRoom)
                .innerJoin(chatRoom.seller, seller).on(seller.userId.eq(sellerUserId))
                .innerJoin(chatRoom.buyer, buyer).on(buyer.userId.eq(buyerUserId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<ChatRoom> findChatRoomAndChatDataById(long chatRoomId) {
        ChatRoom result = jpaQueryFactory.select(chatRoom)
                .from(chatRoom)
                .leftJoin(chatRoom.chatData).fetchJoin()
                .where(chatRoom.chatRoomId.eq(chatRoomId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

}
