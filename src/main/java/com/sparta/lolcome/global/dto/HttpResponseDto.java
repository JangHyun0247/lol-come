package com.sparta.lolcome.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class HttpResponseDto {

//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HttpStatus status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
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
