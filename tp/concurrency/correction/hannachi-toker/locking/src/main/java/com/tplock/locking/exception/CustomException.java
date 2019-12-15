package com.tplock.locking.exception;

import com.tplock.locking.dto.ErrorDefinition;
import org.springframework.http.HttpStatus;

public class CustomException extends Exception {
    protected ErrorDefinition errorDefinition;
    protected HttpStatus httpCode;
    public ErrorDefinition getErrorDefinition(){
        return this.errorDefinition;
    }
    public HttpStatus getHttpCode(){
        return httpCode;
    }
}