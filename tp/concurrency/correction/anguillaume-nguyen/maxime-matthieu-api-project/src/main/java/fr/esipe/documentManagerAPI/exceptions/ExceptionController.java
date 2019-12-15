package fr.esipe.documentManagerAPI.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = BadRequest.class)
    public ResponseEntity<Object> exception (BadRequest exception){
        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Conflict.class)
    public ResponseEntity<Object> exception (Conflict exception){
        return new ResponseEntity<>(" You have to resolve conflict", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = NoContent.class)
    public ResponseEntity<Object> exception (NoContent exception){
        return new ResponseEntity<>("The content of the document is empty", HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = NotFound.class)
    public ResponseEntity<Object> exception (NotFound exception){
        return new ResponseEntity<>("The document hasn't been found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity handleException(GenericException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
