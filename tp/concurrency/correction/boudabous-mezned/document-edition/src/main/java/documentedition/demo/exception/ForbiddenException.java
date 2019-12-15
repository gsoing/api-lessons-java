package documentedition.demo.exception;

import documentedition.demo.dto.ErrorMessage;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends AbstractDocumentException {

    public static final ForbiddenException DEFAULT = new ForbiddenException();

    public static final String FORBIDDEN_CODE = "err.func.wired.forbidden";
    public static final String FORBIDDEN_MESSAGE = "The access is forbidden";

    private ForbiddenException() {
        super(HttpStatus.FORBIDDEN,
                ErrorMessage.builder().build().builder()
                        .code(FORBIDDEN_CODE)
                        .message(FORBIDDEN_MESSAGE)
                        .build());
    }
}