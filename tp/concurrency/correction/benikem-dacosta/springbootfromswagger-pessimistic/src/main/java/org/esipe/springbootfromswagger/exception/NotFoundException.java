package org.esipe.springbootfromswagger.exception;


import org.esipe.springbootfromswagger.model.ErrorMessage;
import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractDocumentException {

    public static final NotFoundException DEFAULT = new NotFoundException();

    public static final String NOT_FOUND_CODE = "err.func.wired.notfound";
    public static final String NOT_FOUND_MESSAGE = "Resource not found";

    private NotFoundException() {
        super(HttpStatus.NOT_FOUND,
                ErrorMessage.builder()
                        .errorCode(NOT_FOUND_CODE)
                        .errorMessage(NOT_FOUND_MESSAGE)
                        .build());
    }
}
