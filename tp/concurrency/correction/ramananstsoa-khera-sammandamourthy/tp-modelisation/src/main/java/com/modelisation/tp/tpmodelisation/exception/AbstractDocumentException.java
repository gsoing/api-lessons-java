package com.modelisation.tp.tpmodelisation.exception;

import com.modelisation.tp.tpmodelisation.dto.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Abstract Document Exception
 */
@Getter
public class AbstractDocumentException extends RuntimeException {

    private final transient ErrorMessage errorMessage;
    private final HttpStatus httpStatus;

    public AbstractDocumentException(HttpStatus httpStatus, ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public AbstractDocumentException(ErrorMessage errorMessage) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }
}
