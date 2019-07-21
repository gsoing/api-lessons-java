package com.gso.samples.tweet.mvc;

import com.gso.samples.tweet.dto.ErrorMessage;
import com.gso.samples.tweet.dto.ErrorMessageType;
import com.gso.samples.tweet.dto.ErrorMessages;
import com.gso.samples.tweet.exception.BadRequestException;
import com.gso.samples.tweet.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
/**
 * On surcharge cette classe fournie par Spring pour gérer les erreurs à notre convenance
 */
public class RestControllerAdvice extends ResponseEntityExceptionHandler  {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage errorMessage =  ErrorMessage.builder().message(ex.getMessage()).code(BadRequestException.BAD_REQUEST_CODE).build();
        ErrorMessages errorMessages = new ErrorMessages(ErrorMessageType.fromStatus(status), errorMessage);
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage errorMessage =  ErrorMessage.builder().message(ex.getMessage()).code(BadRequestException.BAD_REQUEST_CODE).build();
        ErrorMessages errorMessages = new ErrorMessages(ErrorMessageType.fromStatus(status), errorMessage);
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage errorMessage =  ErrorMessage.builder().message(ex.getMessage()).code(BadRequestException.BAD_REQUEST_CODE).build();
        ErrorMessages errorMessages = new ErrorMessages(ErrorMessageType.fromStatus(status), errorMessage);
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    /**
     * déclare une Handler spécifique pour nos exceptions fonctionnelles
     */
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) throws Exception {
        return new ResponseEntity<>(ex.getErrorMessage(), ex.getHttpStatus());
    }
}
