package com.team.leaf.common.exception;

import com.team.leaf.common.message.MessageResponse;
import com.team.leaf.user.account.exception.AccountException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler({AccountException.class})
    public ResponseEntity handleAccountException(Exception e) {
        return ResponseEntity.badRequest().body(MessageResponse.createResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleGlobalException(Exception e) {
        return ResponseEntity.badRequest().body(MessageResponse.createResponse(e.getMessage()));
    }

}
