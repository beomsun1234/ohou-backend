package com.manduljo.ohou;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 공통 응답
 * @param <T>
 */
@Getter
@NoArgsConstructor
public class ApiCommonResponse<T> {
    private String status;
    private String message;
    private T data;

    @Builder
    public ApiCommonResponse(String status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
