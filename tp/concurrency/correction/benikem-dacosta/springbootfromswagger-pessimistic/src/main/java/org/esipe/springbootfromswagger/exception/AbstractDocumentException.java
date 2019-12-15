package org.esipe.springbootfromswagger.exception;

import lombok.Getter;
import org.esipe.springbootfromswagger.model.ErrorMessage;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractDocumentException extends RuntimeException {

    private final transient ErrorMessage errorMessage;
    private final HttpStatus httpStatus;

    public AbstractDocumentException(HttpStatus httpStatus, ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
