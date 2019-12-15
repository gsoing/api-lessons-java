package com.modelisation.tp.tpmodelisation.exception;

import com.modelisation.tp.tpmodelisation.dto.ErrorMessage;
import org.springframework.http.HttpStatus;

/**
 * Forbidden Exception (Error 403)
 */
public class ForbiddenException extends AbstractDocumentException {

    public static final ForbiddenException DEFAULT = new ForbiddenException();

    public static final String FORBIDDEN_CODE = "err.func.wired.forbidden";
    public static final String FORBIDDEN_MESSAGE = "The access is forbidden";

    private ForbiddenException() {
        super(HttpStatus.FORBIDDEN,
                ErrorMessage.builder().build().builder()
                        .errorCode(FORBIDDEN_CODE)
                        .errorMessage(FORBIDDEN_MESSAGE)
                        .build());
    }
}
