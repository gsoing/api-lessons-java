package documentedition.demo.exception;

import documentedition.demo.dto.ErrorMessage;
import org.springframework.http.HttpStatus;

public class ConflictException extends AbstractDocumentException {
    public static final String CONFLICT_CODE = "err.func.wired.conflict";
    public static final String CONFLICT_MESSAGE ="already locked";

    public static final ConflictException DEFAULT = new ConflictException(CONFLICT_MESSAGE);

    public ConflictException(String message) {
        this(CONFLICT_CODE, message);
    }

    public ConflictException(String code, String message) {
        super(HttpStatus.CONFLICT,
                ErrorMessage.builder()
                        .code(code)
                        .message(message)
                        .build());
    }
}