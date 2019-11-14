package com.gso.samples.tweet.exception;

import com.gso.samples.tweet.dto.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractTweetException extends RuntimeException {

    private final transient ErrorMessage errorMessage;
    private final HttpStatus httpStatus;



    public AbstractTweetException(HttpStatus httpStatus, ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public AbstractTweetException(ErrorMessage errorMessage) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }
}
