package com.gso.samples.tweet.endpoint;

import com.gso.samples.tweet.dto.ErrorMessage;
import com.gso.samples.tweet.dto.ErrorMessages;
import com.gso.samples.tweet.exception.ConflictException;
import com.gso.samples.tweet.exception.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.BiFunction;


import static com.gso.samples.tweet.dto.ErrorMessageType.FUNCTIONAL;
import static com.gso.samples.tweet.dto.ErrorMessageType.TECHNICAL;
import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.TRACE;

/**
 *
 * Surcharge le comportement par défaut de la gestion des erreurs
 */
@Slf4j
@RestController
@ConditionalOnWebApplication(type = SERVLET)
public class ErrorController extends AbstractErrorController {

    private static final String PATH = "/error";
    private static final List<HttpStatus> HTTP_STATUS_FUNCTIONAL_ERROR = Arrays.asList(NOT_FOUND, UNAUTHORIZED, FORBIDDEN, CONFLICT);
    private static final Map<HttpStatus, BiFunction<ErrorAttributes, HttpServletRequest, ErrorMessage.ErrorMessageBuilder>> MESSAGES;

    static {
        Map<HttpStatus, BiFunction<ErrorAttributes, HttpServletRequest, ErrorMessage.ErrorMessageBuilder>> messages =
                new EnumMap<>(HttpStatus.class);
        messages.put(NOT_FOUND, ErrorController::notFound);
        messages.put(UNAUTHORIZED, ErrorController::unauthorized);
        messages.put(FORBIDDEN, ErrorController::forbidden);
        messages.put(CONFLICT, ErrorController::conflict);
        MESSAGES = Collections.unmodifiableMap(messages);
    }

    private final ErrorAttributes errorAttributes;

    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
        this.errorAttributes = errorAttributes;
    }

    @SuppressWarnings("squid:S3752")
    @RequestMapping(value = PATH, method = { GET, POST, HEAD, OPTIONS, PUT, PATCH, DELETE, TRACE })
    public ResponseEntity<ErrorMessages> handleErrors(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        ErrorMessage.ErrorMessageBuilder builder = MESSAGES.getOrDefault(status, ErrorController::unknownError)
                .apply(errorAttributes, request);
        return ResponseEntity
                .status(status)
                .body(new ErrorMessages(HTTP_STATUS_FUNCTIONAL_ERROR.contains(status) ? FUNCTIONAL : TECHNICAL,
                        builder.build()));
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    // Don't warn about unused parameters, required for mapping to a BiConsumer
    @SuppressWarnings("squid:S1172")
    private static ErrorMessage.ErrorMessageBuilder notFound(ErrorAttributes errorAttributes,
                                                             HttpServletRequest request) {
        return ErrorMessage.builder()
                .code("err.func.wired.not-found")
                .message("The specified url does not exist");
    }

    // Don't warn about unused parameters, required for mapping to a BiConsumer
    @SuppressWarnings("squid:S1172")
    private static ErrorMessage.ErrorMessageBuilder unauthorized(ErrorAttributes errorAttributes,
                                                                 HttpServletRequest request) {
        return ErrorMessage.builder()
                .code("err.func.wired.unauthorized")
                .message("The access is unauthorized");
    }

    // Don't warn about unused parameters, required for mapping to a BiConsumer
    @SuppressWarnings("squid:S1172")
    private static ErrorMessage.ErrorMessageBuilder forbidden(ErrorAttributes errorAttributes,
                                                              HttpServletRequest request) {
        return ErrorMessage.builder()
                .code(ForbiddenException.FORBIDDEN_CODE)
                .message(ForbiddenException.FORBIDDEN_MESSAGE);
    }

    // Don't warn about unused parameters, required for mapping to a BiConsumer
    @SuppressWarnings("squid:S1172")
    private static ErrorMessage.ErrorMessageBuilder conflict(ErrorAttributes errorAttributes,
                                                             HttpServletRequest request) {
        return ErrorMessage.builder()
                .code(ConflictException.CONFLICT_CODE)
                .message(ConflictException.CONFLICT_MESSAGE);
    }

    private static ErrorMessage.ErrorMessageBuilder unknownError(ErrorAttributes errorAttributes,
                                                                 HttpServletRequest request) {
        Throwable error = errorAttributes.getError(new ServletWebRequest(request));
        if (error == null) {
            log.error("Unknown error caught, but it doesn't contain an exception. Request is: {}", request,
                    new IllegalStateException("Missing exception, logging the call stack"));
        } else {
            log.error("Unexpected exception caught", error);
        }
        return ErrorMessage.builder()
                .code("err.tech.wired.unknown-error")
                .message("An unknown error happened");
    }
}
