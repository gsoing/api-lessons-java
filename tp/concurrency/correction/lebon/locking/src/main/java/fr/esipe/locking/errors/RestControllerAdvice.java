package fr.esipe.locking.errors;

import fr.esipe.locking.enums.ErrorType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedList;

@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler  {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        LinkedList<Error> errors = new LinkedList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(new Error(ErrorCode.BAD_REQUEST_ERROR_CODE, error.getDefaultMessage()));
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(new Error(ErrorCode.BAD_REQUEST_ERROR_CODE, error.getDefaultMessage()));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDefinition.builder().errorType(ErrorType.TECHNICAL.name()).errors(errors).build());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        LinkedList<Error> errors = new LinkedList<>();

        errors.add(new Error(ErrorCode.BAD_REQUEST_ERROR_CODE, ex.getMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDefinition.builder().errorType(ErrorType.TECHNICAL.name()).errors(errors));
    }

}
