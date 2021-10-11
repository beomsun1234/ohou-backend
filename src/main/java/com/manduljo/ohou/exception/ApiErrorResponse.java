package com.manduljo.ohou.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiErrorResponse {
    private String error;
    private String message;


    @Builder
    public ApiErrorResponse(String error, String message){
        this.error = error;
        this.message = message;
    }
}
