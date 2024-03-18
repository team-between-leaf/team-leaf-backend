package com.team.leaf.shopping.search.controller;

import com.team.leaf.shopping.search.dto.AutoCompleteRequest;
import com.team.leaf.shopping.search.dto.UtilInitDto;
import com.team.leaf.shopping.search.service.AutoCompleteService;
import com.team.leaf.user.account.exception.ApiResponse;
import com.team.leaf.user.account.exception.ApiResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auto-complete")
public class AutoCompleteController {

    private final AutoCompleteService autoCompleteService;

    @PostMapping
    @Operation(summary = "자동 완성 글자 추가")
    public ApiResponse addSearchWord(@RequestBody AutoCompleteRequest request) {
        autoCompleteService.addSearchWord(request);

        return new ApiResponse(ApiResponseStatus.SUCCESS);
    }

    @GetMapping
    @Operation(summary = "빈도수를 기반으로 자동 검색 탐색")
    public ApiResponse findSearchComplete(@RequestParam String word) {
        List<UtilInitDto> result = autoCompleteService.findSearchComplete(word);

        return new ApiResponse(result);
    }

}
