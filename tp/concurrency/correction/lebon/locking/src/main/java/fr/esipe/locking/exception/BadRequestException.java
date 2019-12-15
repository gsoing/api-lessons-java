package fr.esipe.locking.exception;

import fr.esipe.locking.enums.ErrorType;
import fr.esipe.locking.errors.Error;
import fr.esipe.locking.errors.ErrorCode;
import fr.esipe.locking.errors.ErrorDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;

public class BadRequestException {

    public static ResponseEntity<?> getError(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDefinition.builder()
                .errorType(ErrorType.FUNCTIONAL.name())
                .errors(new LinkedList<Error>(){{add(new Error(ErrorCode.BAD_REQUEST_ERROR_CODE, message)); }}).build());
    }
}
