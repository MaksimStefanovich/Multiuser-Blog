package com.stefanovich.exception;

public class BadRequestException extends RuntimeException {
   public BadRequestException(String request){
        super(request);
    }
}
