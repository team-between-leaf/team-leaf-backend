package com.team.leaf.socket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.leaf.shopping.chat.dto.ChatMessageRequest;
import com.team.leaf.shopping.chat.dto.ChatMessageResponse;
import com.team.leaf.shopping.chat.dto.MessageType;
import com.team.leaf.shopping.chat.entity.Chat;
import com.team.leaf.shopping.chat.entity.ChatRoom;
import com.team.leaf.shopping.chat.repository.ChatRepository;
import com.team.leaf.shopping.chat.repository.ChatRoomRepository;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;
    private static final Map<Long, List<WebSocketSession>> CHAT_ROOM = new HashMap<>();
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessageRequest body = mapper.readValue(message.getPayload(), ChatMessageRequest.class);
        long chatRoomId = body.getChatRoomId();

        if(body.getMessageType().equals(MessageType.ENTER)) {
            joinChatRoom(chatRoomId, session);
        }

        if(body.getMessageType().equals(MessageType.SEND)) {
            AccountDetail sender = saveChatData(body);

            sendMessage(body, sender);
        }
    }

    private void joinChatRoom(long chatRoomId, WebSocketSession session) {
        if(!CHAT_ROOM.containsKey(chatRoomId)) {
            CHAT_ROOM.put(chatRoomId, new ArrayList<>());
        }
        List<WebSocketSession> sessions = CHAT_ROOM.get(chatRoomId);
        if(!sessions.contains(session)) {
            sessions.add(session);
        }
    }

    private AccountDetail saveChatData(ChatMessageRequest body) {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomAndChatDataById(body.getChatRoomId())
                .orElseThrow(() -> new RuntimeException("해당 채팅방을 찾을 수 없습니다."));

        AccountDetail accountDetail = accountRepository.findById(body.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));

        Chat chat = Chat.createChat(accountDetail, body.getMessage());
        chatRoom.addChatData(chat);

        chatRepository.save(chat);

        return accountDetail;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        for(long chatRoomId : CHAT_ROOM.keySet()) {
            List<WebSocketSession> webSocketSessions = CHAT_ROOM.get(chatRoomId);

            for(int i = 0; i < webSocketSessions.size(); i++) {
                WebSocketSession socket = webSocketSessions.get(i);

                if(socket.getId().equals(session.getId())) {
                    webSocketSessions.remove(i);
                    break;
                }
            }
        }
    }

    private void sendMessage(ChatMessageRequest body, AccountDetail sender) throws IOException {
        List<WebSocketSession> sessions = CHAT_ROOM.get(body.getChatRoomId());
        ChatMessageResponse response = ChatMessageResponse.createChatMessageResponse(body.getMessage(), sender);

        for(WebSocketSession sessionData : sessions) {
            sessionData.sendMessage(new TextMessage(mapper.writeValueAsString(response)));
        }
    }

}