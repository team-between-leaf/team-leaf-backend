package com.team.leaf.alert.controller;

import com.team.leaf.alert.dto.AlertRequest;
import com.team.leaf.alert.dto.SendAlertRequest;
import com.team.leaf.alert.service.AlertService;
import com.team.leaf.common.custom.LogIn;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.exception.ApiResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping(value = "/alert/subscribe" , produces = "text/event-stream")
    @Operation(summary = "SSE 연결을 위한 요청입니다.")
    public SseEmitter subscribeAlert(@LogIn AccountDetail accountDetail) {
        return alertService.subscribeAlert(accountDetail);
    }

    @PostMapping("/alert/message")
    @Operation(summary = "SSE 연결된 대상으로 메세지를 전송합니다.")
    public ApiResponse sendMessageToSubscribe(@RequestBody SendAlertRequest request) {
        alertService.sendMessageToSubscribe(request);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

    @PutMapping("/alert/notify")
    @Operation(summary = "SSE 알림 설정 API [ 사용자 인증 정보 필요 ]")
    public ApiResponse updateAlertNotify(@LogIn @Parameter(hidden = true) AccountDetail accountDetail, @RequestBody AlertRequest request) {
        alertService.updateAlertNotify(accountDetail, request);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

}
