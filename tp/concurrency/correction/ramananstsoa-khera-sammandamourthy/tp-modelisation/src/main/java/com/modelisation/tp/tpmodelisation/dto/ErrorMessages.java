package com.modelisation.tp.tpmodelisation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Error Messages with errorType and errors
 */
@Getter
@NoArgsConstructor
public class ErrorMessages implements Serializable {
    private ErrorMessageType errorType;
    private List<ErrorMessage> errors;

    public ErrorMessages(ErrorMessageType errorType, ErrorMessage... errorMessages) {
        this.errorType = errorType;
        this.errors = Arrays.asList(errorMessages);
    }
}
