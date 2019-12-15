package com.modelisation.tp.tpmodelisation.exception;

import com.modelisation.tp.tpmodelisation.dto.ErrorMessage;
import com.modelisation.tp.tpmodelisation.dto.ErrorMessages;
import org.springframework.http.HttpStatus;

/**
 * Not Found Exception (Error 404)
 */
public class NotFoundException extends AbstractDocumentException {

    public static final NotFoundException DEFAULT = new NotFoundException();

    public static final String NOT_FOUND_CODE = "err.func.wired.notfound";
    public static final String NOT_FOUND_MESSAGE = "The Ressource is not found";

    private NotFoundException() {
        super(HttpStatus.NOT_FOUND,
                ErrorMessage.builder()
                        .errorCode(NOT_FOUND_CODE)
                        .errorMessage(NOT_FOUND_MESSAGE)
                        .build());
    }
}
