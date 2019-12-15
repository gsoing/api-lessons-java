package org.esipe.springbootfromswagger.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    TECHNICAL(HttpStatus.INTERNAL_SERVER_ERROR),
    FUNCTIONAL(HttpStatus.BAD_REQUEST);

    private HttpStatus status;

    ErrorType(HttpStatus status) {
        this.status = status;
    }

    public static ErrorType fromStatus(HttpStatus status) {
        if (status.is4xxClientError()) {
            return FUNCTIONAL;
        } else if (status.is5xxServerError()) {
            return TECHNICAL;
        }
        throw new IllegalArgumentException("HTTP status '" + status + "'is not valid exception status ");
    }
}
