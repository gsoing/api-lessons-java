package esipe.tp.com.gestionconcurence.exception;

import esipe.tp.com.gestionconcurence.dto.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractDocumentException extends RuntimeException {

    private final transient Error error;
    private final HttpStatus httpStatus;


    public AbstractDocumentException(HttpStatus httpStatus, Error error) {
        super(error.getMessage());
        this.error = error;
        this.httpStatus = httpStatus;
    }

    public AbstractDocumentException(Error error) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, error);
    }
}
