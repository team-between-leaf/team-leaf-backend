package com.team.leaf.alert.service;

import com.team.leaf.alert.dto.AlertRequest;
import com.team.leaf.alert.dto.AlertType;
import com.team.leaf.alert.dto.AlertTypeQuery;
import com.team.leaf.alert.dto.SendAlertRequest;
import com.team.leaf.alert.entity.Alert;
import com.team.leaf.alert.repository.AlertRepository;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.entity.AccountPrivacy;
import com.team.leaf.user.account.jwt.JwtTokenUtil;
import com.team.leaf.user.account.jwt.PrincipalDetails;
import com.team.leaf.user.account.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.team.leaf.alert.dto.AlertType.*;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final AccountRepository accountRepository;
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; // 60 second
    private Map<Long , SseEmitter> session = new HashMap<>();

    @Transactional
    public void updateAlertNotify(String token, AlertRequest request) {
        String email = jwtTokenUtil.getEmailFromToken(token);

        AccountDetail account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("not fount User"));

        updateNotifyData(account.getUserDetail(), request);
    }

    private void updateNotifyData(AccountPrivacy account, AlertRequest request) {
        if(request.getAlertType().equals(ORDER_DELIVERY)) {
            account.updateOrderDeliveryNotify(request.isSetting());
        }
        else if(request.getAlertType().equals(FOLLOWED_SELLER)) {
            account.updateFollowedSellerNotify(request.isSetting());
        }
        else if(request.getAlertType().equals(MARKETING)) {
            account.updateMarketingNotify(request.isSetting());
        }
        else if(request.getAlertType().equals(WISHLIST)) {
            account.updateWishlistNotify(request.isSetting());
        }
    }

    public SseEmitter subscribeAlert(String token) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        String email = jwtTokenUtil.getEmailFromToken(token);
        AccountDetail account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("not fount User"));

        long id = account.getUserId();

        if(session.containsKey(id)) {
            session.remove(id);
        }
        session.put(id , emitter);

        sendToClient(emitter , id , "EventStream Created. [userId = " + id + " ]");

        return emitter;
    }

    private void sendToClient(SseEmitter emitter, long id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(Long.toString(id))
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            throw new RuntimeException("SSE 연결을 실패했습니다.");
        }
    }

    public void sendMessageToSubscribe(SendAlertRequest request) {
        AlertTypeQuery type = conversionAlertType(request.getAlertType());

        List<Long> accountId = alertRepository.findAccountIdByNoticeAgreedTo(type);

        for(Long id : accountId) {
            if(session.containsKey(id)) {
                sendToClient(session.get(id), id, request.getMessage());
            }
        }
    }

    private AlertTypeQuery conversionAlertType(AlertType type) {
        if(type == ORDER_DELIVERY) {
            return AlertTypeQuery.ORDER_DELIVERY;
        }
        if(type == FOLLOWED_SELLER) {
            return AlertTypeQuery.FOLLOWED_SELLER;
        }
        if(type == MARKETING) {
            return AlertTypeQuery.MARKETING;
        }
        if(type == WISHLIST) {
            return AlertTypeQuery.WISHLIST;
        }

        return null;
    }
}
