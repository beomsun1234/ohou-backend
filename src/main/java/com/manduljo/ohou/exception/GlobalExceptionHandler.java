package com.manduljo.ohou.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiErrorResponse> handlerException(Exception e){
        return new ResponseEntity<ApiErrorResponse>(ApiErrorResponse.builder().message(e.getMessage()).error("bad request").build(), HttpStatus.BAD_REQUEST);
    }
}