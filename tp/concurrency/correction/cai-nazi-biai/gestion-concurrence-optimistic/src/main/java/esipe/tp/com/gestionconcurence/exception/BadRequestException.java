package esipe.tp.com.gestionconcurence.exception;

import esipe.tp.com.gestionconcurence.dto.Error;
import org.springframework.http.HttpStatus;

public class BadRequestException extends AbstractDocumentException {

    public static final BadRequestException DEFAULT = new BadRequestException();

    public static final String BAD_REQUEST_CODE = "err.func.wired.badrequest";
    public static final String BAS_REQUEST_MESSAGE = "No found";

    private BadRequestException() {
        super(HttpStatus.BAD_REQUEST,
                Error.builder()
                        .message(BAS_REQUEST_MESSAGE)
                        .code(BAD_REQUEST_CODE)
                        .build());
    }
}