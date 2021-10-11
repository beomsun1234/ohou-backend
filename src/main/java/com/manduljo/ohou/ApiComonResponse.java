package com.manduljo.ohou;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiComonResponse<T> {
    private String status;
    private String message;
    private T data;

    @Builder
    public ApiComonResponse(String status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
