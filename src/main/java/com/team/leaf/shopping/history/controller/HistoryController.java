package com.team.leaf.shopping.history.controller;

import com.team.leaf.common.custom.LogIn;
import com.team.leaf.shopping.history.dto.HistoryRequest;
import com.team.leaf.shopping.history.entity.History;
import com.team.leaf.shopping.history.service.HistoryService;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.exception.ApiResponseStatus;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    @DeleteMapping("/{historyId}")
    public ApiResponse deleteHistory(@PathVariable long historyId, @LogIn @Parameter(hidden = true) AccountDetail accountDetail) {
        historyService.deleteHistory(historyId, accountDetail);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

    @PostMapping
    public ApiResponse addHistory(@RequestBody HistoryRequest request, @LogIn @Parameter(hidden = true) AccountDetail accountDetail) {
        historyService.addHistory(request, accountDetail);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

    @GetMapping
    public ApiResponse getAllHistory(@LogIn @Parameter(hidden = true) AccountDetail accountDetail) {
        historyService.getAllHistory(accountDetail);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

    @DeleteMapping
    public ApiResponse deleteAllHistory(@LogIn @Parameter(hidden = true) AccountDetail accountDetail) {
        historyService.deleteAllHistory(accountDetail);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

}
