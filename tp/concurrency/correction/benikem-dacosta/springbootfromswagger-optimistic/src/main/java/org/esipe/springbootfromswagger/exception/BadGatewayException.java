package org.esipe.springbootfromswagger.exception;

import org.esipe.springbootfromswagger.model.ErrorMessage;
import org.springframework.http.HttpStatus;

public class BadGatewayException extends AbstractDocumentException {
    public static final BadGatewayException DEFAULT = new BadGatewayException();

    public static final String BAD_GATEWAY_CODE = "err.func.wired.badGateway";
    public static final String BAD_GATEWAY_MESSAGE = "Server received an invalid response";


    public BadGatewayException() {
        super(HttpStatus.BAD_GATEWAY,
                ErrorMessage.builder()
                        .errorCode(BAD_GATEWAY_CODE)
                        .errorMessage(BAD_GATEWAY_MESSAGE)
                        .build());
    }
}
