package com.aliemreky.couriertracking.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Getter
@Builder
public class ResponseModel<T> implements Serializable {
    T responseEntity;
    Date dateTime;
    HttpStatus statusCode;
    String errorMessage;
    Map<String, String> errors;

    public static <T> ResponseModel<T> success(T model){
        return ResponseModel.<T>builder()
                .statusCode(HttpStatus.OK)
                .dateTime(new Date())
                .responseEntity(model)
                .build();

    }

    public static <T> ResponseModel<T> success(T model,HttpStatus status){
        return ResponseModel.<T>builder()
                .statusCode(status)
                .dateTime(new Date())
                .responseEntity(model)
                .build();

    }

    public static <T> ResponseModel<T> warning(T model,String warningMessage,HttpStatus statusCode){
        return ResponseModel.<T>builder()
                .statusCode(statusCode)
                .dateTime(new Date())
                .errorMessage(warningMessage)
                .responseEntity(model)
                .build();

    }
}
