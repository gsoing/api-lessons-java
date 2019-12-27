package esipe.tp.com.gestionconcurence.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class DocumentErrorAdvice {

    //500
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRunTimeException(RuntimeException e) {
        return error(INTERNAL_SERVER_ERROR, e);
    }

    //404
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return error(NOT_FOUND, e);
    }
    //403
    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<String> handleForbiddenException(NotFoundException e) {
        return error(FORBIDDEN, e);
    }
    //401
    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<String> handleUnauthorizedException(NotFoundException e) {
        return error(UNAUTHORIZED, e);
    }
    //
    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<String> handleConflictException(NotFoundException e) {
        return error(CONFLICT, e);
    }

    private ResponseEntity<String> error(HttpStatus status, Exception e) {
        log.error("Exception : ", e);
        return ResponseEntity.status(status).body(e.getMessage());
    }
}