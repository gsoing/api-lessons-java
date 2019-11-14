package com.gso.samples.tweet.exception;

import com.gso.samples.tweet.dto.ErrorMessage;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends AbstractTweetException {

    public static final ForbiddenException DEFAULT = new ForbiddenException();

    public static final String FORBIDDEN_CODE = "err.func.wired.forbidden";
    public static final String FORBIDDEN_MESSAGE = "The access is forbidden";

    private ForbiddenException() {
        super(HttpStatus.FORBIDDEN,
                ErrorMessage.builder().build().builder()
                        .code(FORBIDDEN_CODE)
                        .message(FORBIDDEN_MESSAGE)
                        .build());
    }
}
