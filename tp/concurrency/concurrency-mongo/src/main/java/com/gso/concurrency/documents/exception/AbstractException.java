package com.gso.concurrency.documents.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AbstractException extends RuntimeException {

    HttpStatus httpStatus;
    String errorCode;
    String errorMessage;

    public AbstractException(HttpStatus httpStatus, String errorCode, String errorMessage){
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
