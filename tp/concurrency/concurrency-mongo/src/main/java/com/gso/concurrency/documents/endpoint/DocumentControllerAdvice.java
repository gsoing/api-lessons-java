package com.gso.concurrency.documents.endpoint;

import com.gso.concurrency.documents.dto.ErrorDto;
import com.gso.concurrency.documents.exception.AbstractException;
import com.gso.concurrency.documents.exception.ConflictException;
import com.gso.concurrency.documents.exception.NotFoundException;
import com.gso.concurrency.documents.exception.PreconditionFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class DocumentControllerAdvice {

    @ExceptionHandler({NotFoundException.class, PreconditionFailedException.class})
    public final ResponseEntity<ErrorDto> handleNotFound(AbstractException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(
                        ErrorDto
                                .builder()
                                .errorCode(ex.getErrorCode())
                                .errorMessage(ex.getErrorMessage())
                                .build()
                );
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<ErrorDto> handleConflict(ConflictException ex){
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(
                        ErrorDto
                                .builder()
                                .errorCode(ex.getErrorCode())
                                .errorMessage(ex.getErrorMessage())
                                .build()
                );
    }
}
