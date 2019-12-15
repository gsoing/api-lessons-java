package org.esipe.springbootfromswagger.exception;

import org.esipe.springbootfromswagger.model.ErrorMessage;
import org.springframework.http.HttpStatus;

public class PreconditionException extends AbstractDocumentException {

    public static final PreconditionException DEFAULT = new PreconditionException();

    public static final String PRECONDITION_FAILED_CODE = "err.func.wired.precondition.failed";
    public static final String PRECONDITION_FAILED_MESSAGE = "A precondition has failed";

    private PreconditionException() {
        super(HttpStatus.PRECONDITION_FAILED,
                ErrorMessage.builder()
                        .errorCode(PRECONDITION_FAILED_CODE)
                        .errorMessage(PRECONDITION_FAILED_MESSAGE)
                        .build());
    }
}