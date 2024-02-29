package com.team.leaf.alert.controller;

import com.team.leaf.alert.dto.AlertRequest;
import com.team.leaf.alert.service.AlertService;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.exception.ApiResponseStatus;
import com.team.leaf.user.account.jwt.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping(value = "/alert/subscribe/{id}" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ApiResponse subscribeAlert(@PathVariable long id) {
        SseEmitter sseEmitter = alertService.subscribeAlert(id);

        return new ApiResponse(null);
    }

    @PutMapping("/alert/notify")
    public ApiResponse updateAlertNotify(@AuthenticationPrincipal PrincipalDetails userDetails , @RequestBody AlertRequest request) {
        alertService.updateAlertNotify(userDetails.getAccountDetail(), request);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

}
