package com.team.leaf.alert.controller;

import com.team.leaf.alert.dto.AlertRequest;
import com.team.leaf.alert.dto.SendAlertRequest;
import com.team.leaf.alert.service.AlertService;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.exception.ApiResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping(value = "/alert/subscribe" , produces = "text/event-stream")
    @Operation(summary = "SSE 연결을 위한 요청입니다.")
    public SseEmitter subscribeAlert(@RequestHeader(value = "Authorization") String authorizationHeader) {
        return alertService.subscribeAlert(authorizationHeader);
    }

    @PostMapping("/alert/message")
    @Operation(summary = "SSE 연결된 대상으로 메세지를 전송합니다.")
    public ApiResponse sendMessageToSubscribe(@RequestBody SendAlertRequest request) {
        alertService.sendMessageToSubscribe(request);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

    @PutMapping("/alert/notify")
    @Operation(summary = "SSE 알림 설정 API")
    public ApiResponse updateAlertNotify(@RequestHeader(value = "Authorization") String authorizationHeader , @RequestBody AlertRequest request) {
        alertService.updateAlertNotify(authorizationHeader, request);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

}
