package com.modelisation.tp.tpmodelisation.exception;

import com.modelisation.tp.tpmodelisation.dto.ErrorMessage;
import org.springframework.http.HttpStatus;

/**
 * Manage Internal Server Exception (Error 500)
 */
public class InternalServerException extends AbstractDocumentException {

    public static final InternalServerException DEFAULT = new InternalServerException();

    public static final String INTERNAL_SERVER_CODE = "err.func.wired.server";
    public static final String INTERNAL_SERVER_MESSAGE = "The server encountered an internal error or misconfiguration and was unable to complete your request";

    private InternalServerException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorMessage.builder()
                        .errorCode(INTERNAL_SERVER_CODE)
                        .errorMessage(INTERNAL_SERVER_MESSAGE)
                        .build());
    }
}
