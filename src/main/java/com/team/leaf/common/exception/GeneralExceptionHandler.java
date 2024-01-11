package com.team.leaf.common.exception;

import com.team.leaf.common.message.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleGlobalException(Exception e) {
        return ResponseEntity.badRequest().body(MessageResponse.createResponse(e.getMessage()));
    }

}
