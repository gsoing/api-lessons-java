package esipe.tp.com.gestionconcurence.exception;

import esipe.tp.com.gestionconcurence.dto.Error;
import org.springframework.http.HttpStatus;

public class ConflictException extends AbstractDocumentException {
    public static final String CONFLICT_CODE = "err.func.wired.conflict";
    public static final String CONFLICT_MESSAGE =
            "Un verrou est déja poser sur ce document";

    public static final ConflictException DEFAULT = new ConflictException(CONFLICT_MESSAGE);

    public ConflictException(String message) {
        this(CONFLICT_CODE, message);
    }

    public ConflictException(String code, String message) {
        super(HttpStatus.CONFLICT,
         Error.builder()
                .message(message)
                .code(code)
                .build());
    }
}
