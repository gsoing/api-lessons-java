package org.esipe.springbootfromswagger.exception;

import org.esipe.springbootfromswagger.model.ErrorMessage;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends AbstractDocumentException {

    public static final ForbiddenException DEFAULT = new ForbiddenException();

    public static final String FORBIDDEN_CODE = "err.func.wired.forbidden";
    public static final String FORBIDDEN_MESSAGE = "The access is forbidden";


    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN,
                ErrorMessage.builder()
                        .errorCode(FORBIDDEN_CODE)
                        .errorMessage(FORBIDDEN_MESSAGE)
                        .build());
    }
}
