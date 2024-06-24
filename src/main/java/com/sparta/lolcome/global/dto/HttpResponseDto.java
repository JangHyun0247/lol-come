package com.sparta.lolcome.global.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class HttpResponseDto {

    private HttpStatus status;
    private String message;
    private Object data;


    public HttpResponseDto(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

    public HttpResponseDto(HttpStatus status, String message, Object data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
