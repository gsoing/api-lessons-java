package esipe.tp.com.gestionconcurence.exception;

import esipe.tp.com.gestionconcurence.dto.Error;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends AbstractDocumentException {

    public static final ForbiddenException DEFAULT = new ForbiddenException();

    public static final String FORBIDDEN_CODE = "err.func.wired.forbidden";
    public static final String FORBIDDEN_MESSAGE = "Forbidden";

    private ForbiddenException() {
        super(HttpStatus.FORBIDDEN,
                Error.builder().build().builder()
                        .code(FORBIDDEN_CODE)
                        .message(FORBIDDEN_MESSAGE)
                        .build());
    }
}
