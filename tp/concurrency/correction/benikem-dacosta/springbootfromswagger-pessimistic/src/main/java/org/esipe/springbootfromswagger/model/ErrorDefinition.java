package org.esipe.springbootfromswagger.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class ErrorDefinition {
    private ErrorType errorType;
    private List<ErrorMessage> errors;

    public ErrorDefinition(ErrorType errorType, ErrorMessage... errors) {
        this.errorType = errorType;
        this.errors = Arrays.asList(errors);
    }
}
