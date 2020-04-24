package com.example.demo.exception.exceptionHandler;

import lombok.Data;

import java.util.Date;

@Data
public class ExceptionResponse {
    private Date timestamp;

    private String message;


    public ExceptionResponse(Date date, String message) {
        this.timestamp = date;
        this.message = message;
    }
}
