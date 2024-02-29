package com.team.leaf.alert.controller;

import com.team.leaf.alert.dto.AlertRequest;
import com.team.leaf.alert.dto.SendAlertRequest;
import com.team.leaf.alert.service.AlertService;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.exception.ApiResponseStatus;
import com.team.leaf.user.account.jwt.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping(value = "/alert/subscribe" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ApiResponse subscribeAlert(@AuthenticationPrincipal PrincipalDetails userDetails) {
        alertService.subscribeAlert(userDetails);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

    @PostMapping("/alert/message")
    public ApiResponse sendMessageToSubscribe(@RequestBody SendAlertRequest request) {
        alertService.sendMessageToSubscribe(request);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

    @PutMapping("/alert/notify")
    public ApiResponse updateAlertNotify(@AuthenticationPrincipal PrincipalDetails userDetails , @RequestBody AlertRequest request) {
        alertService.updateAlertNotify(userDetails.getAccountDetail(), request);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

}
