package esipe.tp.com.gestionconcurence.exception;

import esipe.tp.com.gestionconcurence.dto.Error;
import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractDocumentException {

    public static final NotFoundException DEFAULT = new NotFoundException();

    public static final String NOT_FOUND_CODE = "err.func.wired.notfound";
    public static final String NOT_FOUND__MESSAGE = "la ressource n’existe pas";

    private NotFoundException() {
        super(HttpStatus.NOT_FOUND,
                Error.builder()
                        .code(NOT_FOUND_CODE)
                        .message(NOT_FOUND__MESSAGE)
                        .build());
    }
}
