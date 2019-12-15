package esipe.tp.com.gestionconcurence.exception;

import esipe.tp.com.gestionconcurence.dto.Error;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends AbstractDocumentException {

    public static final UnauthorizedException DEFAULT = new UnauthorizedException();

    public static final String UNAUTHORIZED_CODE = "err.func.wired.notfound";
    public static final String UNAUTHORIZED_MESSAGE = "Vous n'etes pas autorisé";

    private UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED,
                Error.builder().code(UNAUTHORIZED_CODE).message(UNAUTHORIZED_MESSAGE)
                        .build());
    }
}
