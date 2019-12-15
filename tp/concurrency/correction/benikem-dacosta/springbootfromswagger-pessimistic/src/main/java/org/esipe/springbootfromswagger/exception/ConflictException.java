package org.esipe.springbootfromswagger.exception;

import org.esipe.springbootfromswagger.model.ErrorMessage;
import org.springframework.http.HttpStatus;

public class ConflictException extends AbstractDocumentException {
    public static final String CONFLICT_CODE = "err.func.wired.conflict";
    public static final String CONFLICT_MESSAGE =
            "The request could not be completed due to a conflict with the current state of the target resource";

    public static final ConflictException DEFAULT = new ConflictException();


    public ConflictException() {
        super(HttpStatus.CONFLICT,
                ErrorMessage.builder()
                        .errorCode(CONFLICT_CODE)
                        .errorMessage(CONFLICT_MESSAGE)
                        .build());
    }
}