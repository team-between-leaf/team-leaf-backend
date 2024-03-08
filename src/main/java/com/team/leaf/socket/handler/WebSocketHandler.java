package com.team.leaf.socket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.leaf.shopping.chat.dto.ChatMessageRequest;
import com.team.leaf.shopping.chat.dto.ChatMessageResponse;
import com.team.leaf.shopping.chat.dto.ChatRoomResponse;
import com.team.leaf.shopping.chat.dto.MessageType;
import com.team.leaf.shopping.chat.entity.Chat;
import com.team.leaf.shopping.chat.entity.ChatRoom;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;
    private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<>();
    private static final Map<Long, Set<WebSocketSession>> CHAT_ROOM = new HashMap<>();
    private final ChatRoomRepository chatRoomRepository;
    private final AccountRepository accountRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        CLIENTS.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessageRequest body = mapper.readValue(message.getPayload(), ChatMessageRequest.class);
        long chatRoomId = body.getChatRoomId();

        if(!CHAT_ROOM.containsKey(chatRoomId)) {
            CHAT_ROOM.put(chatRoomId, new HashSet<>());
        }

        Set<WebSocketSession> webSocketSessions = CHAT_ROOM.get(chatRoomId);
        if(body.getMessageType().equals(MessageType.ENTER)) {
            CHAT_ROOM.get(chatRoomId).add(session);
        }

        if(body.getMessageType().equals(MessageType.SEND)) {
            AccountDetail sender = saveChatData(body);

            sendMessage(webSocketSessions, body, sender);
        }
    }

    @Transactional
    private AccountDetail saveChatData(ChatMessageRequest body) {
        ChatRoom chatRoom = chatRoomRepository.findById(body.getChatRoomId())
                .orElseThrow(() -> new RuntimeException("해당 채팅방을 찾을 수 없습니다."));

        AccountDetail accountDetail = accountRepository.findById(body.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));

        Chat chat = Chat.createChat(accountDetail, body.getMessage());
        chatRoom.addChatData(chat);

        return accountDetail;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        CLIENTS.remove(session.getId());
    }

    private void sendMessage(Set<WebSocketSession> webSocketSessions, ChatMessageRequest body, AccountDetail sender) throws IOException {
        ChatMessageResponse response = ChatMessageResponse.createChatMessageResponse(body.getMessage(), sender);

        for(WebSocketSession sessionData : webSocketSessions) {
            sessionData.sendMessage(new TextMessage(mapper.writeValueAsString(response)));
        }
    }

}
