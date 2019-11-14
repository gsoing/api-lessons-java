package com.gso.samples.tweet.exception;

import com.gso.samples.tweet.dto.ErrorMessage;
import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractTweetException {

    public static final NotFoundException DEFAULT = new NotFoundException();

    public static final String NOT_FOUND_CODE = "err.func.wired.notfound";
    public static final String NOT_FOUND_MESSAGE = "The Ressource is not foud";

    private NotFoundException() {
        super(HttpStatus.NOT_FOUND,
                ErrorMessage.builder()
                        .code(NOT_FOUND_CODE)
                        .message(NOT_FOUND_MESSAGE)
                        .build());
    }
}
