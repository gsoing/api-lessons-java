package fr.esipe.locking.exception;

import fr.esipe.locking.enums.ErrorType;
import fr.esipe.locking.errors.Error;
import fr.esipe.locking.errors.ErrorCode;
import fr.esipe.locking.errors.ErrorDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;

public class NotFoundException {

    public static final String NOT_FOUND_MESSAGE = "The resource is not found";

    public static ResponseEntity<?> getError() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorDefinition.builder()
                .errorType(ErrorType.FUNCTIONAL.name())
                .errors(new LinkedList<Error>(){{add(new Error(ErrorCode.NOT_FOUND_ERROR_CODE, NOT_FOUND_MESSAGE)); }}).build());
    }
}
