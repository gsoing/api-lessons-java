package org.esipe.springbootfromswagger.exception;

import org.esipe.springbootfromswagger.model.ErrorMessage;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends AbstractDocumentException {

    public static final UnauthorizedException DEFAULT = new UnauthorizedException();

    public static final String UNAUTHORIZED_CODE = "err.func.wired.notfound";
    public static final String UNAUTHORIZED_MESSAGE = "The Ressource is not foud";

    private UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED,
                ErrorMessage.builder()
                        .errorCode(UNAUTHORIZED_CODE)
                        .errorMessage(UNAUTHORIZED_MESSAGE)
                        .build());
    }
}
