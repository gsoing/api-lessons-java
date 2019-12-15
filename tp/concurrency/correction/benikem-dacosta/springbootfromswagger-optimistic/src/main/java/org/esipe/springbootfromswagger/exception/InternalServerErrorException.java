package org.esipe.springbootfromswagger.exception;

import org.esipe.springbootfromswagger.model.ErrorMessage;
import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends AbstractDocumentException {

    public static final InternalServerErrorException DEFAULT = new InternalServerErrorException();

    public static final String INTERNAL_SERVER_ERROR_CODE = "err.func.wired.internal.server.error";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";


    public InternalServerErrorException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorMessage.builder()
                        .errorCode(INTERNAL_SERVER_ERROR_CODE)
                        .errorMessage(INTERNAL_SERVER_ERROR_MESSAGE)
                        .build());
    }
}