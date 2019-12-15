package org.esipe.springbootfromswagger.exception;

import org.esipe.springbootfromswagger.model.ErrorMessage;
import org.springframework.http.HttpStatus;

public class NotImplementedException extends AbstractDocumentException {
    public static final NotImplementedException DEFAULT = new NotImplementedException();

    public static final String NOT_IMPLEMENTED_CODE = "err.func.wired.notimplemented";
    public static final String NOT_IMPEMENTED_MESSAGE = "Wanted functionnality not supported by the server";


    public NotImplementedException() {
        super(HttpStatus.NOT_IMPLEMENTED,
                ErrorMessage.builder()
                        .errorCode(NOT_IMPLEMENTED_CODE)
                        .errorMessage(NOT_IMPEMENTED_MESSAGE)
                        .build());
    }
}