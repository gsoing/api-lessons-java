package com.gso.samples.tweet.exception;

import com.gso.samples.tweet.dto.ErrorMessage;
import org.springframework.http.HttpStatus;

public class BadRequestException extends AbstractTweetException {

    public static final BadRequestException DEFAULT = new BadRequestException();

    public static final String BAD_REQUEST_CODE = "err.func.wired.badrequest";
    public static final String BAS_REQUEST_MESSAGE = "The request is bad formated";

    private BadRequestException() {
        super(HttpStatus.BAD_REQUEST,
                ErrorMessage.builder()
                        .code(BAD_REQUEST_CODE)
                        .message(BAS_REQUEST_MESSAGE)
                        .build());
    }
}