package com.manduljo.ohou.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String,Object>> handlerException(Exception e){
        Map<String,Object> map = new HashMap<>();
        map.put("message",e.getMessage());
        map.put("result","fail");
        map.put("data", null);
        map.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
