package com.modelisation.tp.tpmodelisation.exception;

import com.modelisation.tp.tpmodelisation.dto.ErrorMessage;
import org.springframework.http.HttpStatus;

/**
 * Bad Request Exception (Error 403)
 */
public class BadRequestException extends AbstractDocumentException {

    public static final BadRequestException DEFAULT = new BadRequestException();

    public static final String BAD_REQUEST_CODE = "err.func.wired.badrequest";
    public static final String BAS_REQUEST_MESSAGE = "The request is bad formated";

    private BadRequestException() {
        super(HttpStatus.BAD_REQUEST,
                ErrorMessage.builder()
                        .errorCode(BAD_REQUEST_CODE)
                        .errorMessage(BAS_REQUEST_MESSAGE)
                        .build());
    }
}