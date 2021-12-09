package com.manduljo.ohou.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiErrorResponse {
    private String error;
    private String message;
    private String code;

    @Builder
    public ApiErrorResponse(String error, String message,String code){
        this.error = error;
        this.message = message;
        this.code = code;
    }
}
