package com.manduljo.ohou.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiErrorResponse> handlerException(Exception e){
        return new ResponseEntity<ApiErrorResponse>(ApiErrorResponse
                .builder()
                .message(e.getMessage())
                .error("bad request")
                .code(String.valueOf(HttpStatus.BAD_REQUEST))
                .build(), HttpStatus.BAD_REQUEST);
    }
}
