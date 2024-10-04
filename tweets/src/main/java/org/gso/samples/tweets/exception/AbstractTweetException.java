package org.gso.samples.tweets.exception;

import lombok.Getter;
import org.gso.samples.tweets.dto.ErrorMessage;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractTweetException extends RuntimeException {

    private final transient ErrorMessage errorMessage;
    private final HttpStatus httpStatus;



    public AbstractTweetException(HttpStatus httpStatus, ErrorMessage errorMessage) {
        super(errorMessage.message());
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public AbstractTweetException(ErrorMessage errorMessage) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }
}
