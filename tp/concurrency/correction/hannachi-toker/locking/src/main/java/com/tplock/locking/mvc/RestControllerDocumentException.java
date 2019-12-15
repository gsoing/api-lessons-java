package com.tplock.locking.mvc;

import com.tplock.locking.dto.ErrorDefinition;
import com.tplock.locking.dto.ErrorDefinitionType;
import com.tplock.locking.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestControllerDocumentException extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ConflictException.class, NoContentException.class, BadRequestException.class, NotFoundException.class})
    protected ResponseEntity<ErrorDefinition> conflictExceptionHandler(CustomException e){
        return new ResponseEntity<>(e.getErrorDefinition(), e.getHttpCode());
    }

    /* non fonctionnel
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDefinition errorDefinition  =  ErrorDefinition.builder().message(ex.getMessage()).code(BadRequestException.BAD_REQUEST_CODE).build();
        ErrorDefinition errorDefinition = new ErrorDefinition(ErrorDefinitionType.fromStatus(status), errorMessage);
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }
    */
}