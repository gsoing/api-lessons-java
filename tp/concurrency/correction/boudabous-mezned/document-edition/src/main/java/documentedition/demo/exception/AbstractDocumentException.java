package documentedition.demo.exception;

import org.springframework.http.HttpStatus;

import documentedition.demo.dto.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractDocumentException extends RuntimeException {

    private final transient ErrorMessage errorMessage;
    private final HttpStatus httpStatus;

    public AbstractDocumentException(HttpStatus httpStatus, ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

}
