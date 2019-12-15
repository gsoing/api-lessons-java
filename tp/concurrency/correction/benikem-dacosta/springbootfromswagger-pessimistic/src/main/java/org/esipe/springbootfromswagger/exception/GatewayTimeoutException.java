package org.esipe.springbootfromswagger.exception;

import org.esipe.springbootfromswagger.model.ErrorMessage;
import org.springframework.http.HttpStatus;

public class GatewayTimeoutException extends AbstractDocumentException {
    public static final GatewayTimeoutException DEFAULT = new GatewayTimeoutException();

    public static final String GATEWAY_TIMEOUT_CODE = "err.func.wired.gatewayTimeout";
    public static final String GATEWAY_TIMEOUT_MESSAGE = "Response timeout";


    public GatewayTimeoutException() {
        super(HttpStatus.GATEWAY_TIMEOUT,
                ErrorMessage.builder()
                        .errorCode(GATEWAY_TIMEOUT_CODE)
                        .errorMessage(GATEWAY_TIMEOUT_MESSAGE)
                        .build());
    }
}
