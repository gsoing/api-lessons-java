package org.esipe.springbootfromswagger.controller;

import lombok.extern.slf4j.Slf4j;
import org.esipe.springbootfromswagger.exception.*;
import org.esipe.springbootfromswagger.model.ErrorDefinition;
import org.esipe.springbootfromswagger.model.ErrorMessage;
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

import static org.esipe.springbootfromswagger.model.ErrorType.FUNCTIONAL;
import static org.esipe.springbootfromswagger.model.ErrorType.TECHNICAL;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Slf4j
@RestController
public class ErrorController extends AbstractErrorController {

    private static final String PATH = "/error";
    private static final List<HttpStatus> HTTP_STATUS_FUNCTIONAL_ERROR = Arrays.asList(NOT_FOUND, UNAUTHORIZED, FORBIDDEN, CONFLICT, BAD_REQUEST);
    private static final Map<HttpStatus, BiFunction<ErrorAttributes, HttpServletRequest, ErrorMessage.ErrorMessageBuilder>> MESSAGES;

    static {
        Map<HttpStatus, BiFunction<ErrorAttributes, HttpServletRequest, ErrorMessage.ErrorMessageBuilder>> messages =
                new EnumMap<>(HttpStatus.class);
        messages.put(NOT_FOUND, ErrorController::notFound);
        messages.put(UNAUTHORIZED, ErrorController::unauthorized);
        messages.put(FORBIDDEN, ErrorController::forbidden);
        messages.put(CONFLICT, ErrorController::conflict);
        messages.put(BAD_REQUEST, ErrorController::badRequest);
        messages.put(INTERNAL_SERVER_ERROR, ErrorController::internalServerError);
        messages.put(NOT_IMPLEMENTED, ErrorController::notImplemented);
        messages.put(BAD_GATEWAY, ErrorController::badGateway);
        messages.put(GATEWAY_TIMEOUT, ErrorController::gatewayTimeout);
        MESSAGES = Collections.unmodifiableMap(messages);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    private final ErrorAttributes errorAttributes;

    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = PATH, method = {GET, POST, HEAD, OPTIONS, PUT, PATCH, DELETE, TRACE})
    public ResponseEntity<ErrorDefinition> handleErrors(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        ErrorMessage.ErrorMessageBuilder builder = MESSAGES.getOrDefault(status, ErrorController::unknownError)
                .apply(errorAttributes, request);
        return ResponseEntity
                .status(status)
                .body(new ErrorDefinition(HTTP_STATUS_FUNCTIONAL_ERROR.contains(status) ? FUNCTIONAL : TECHNICAL,
                        builder.build()));
    }

    private static ErrorMessage.ErrorMessageBuilder notFound(ErrorAttributes errorAttributes, HttpServletRequest request) {
        return ErrorMessage.builder()
                .errorCode(NotFoundException.NOT_FOUND_CODE)
                .errorMessage(NotFoundException.NOT_FOUND_MESSAGE);
    }

    private static ErrorMessage.ErrorMessageBuilder forbidden(ErrorAttributes errorAttributes,
                                                              HttpServletRequest request) {
        return ErrorMessage.builder()
                .errorCode(ForbiddenException.FORBIDDEN_CODE)
                .errorMessage(ForbiddenException.FORBIDDEN_MESSAGE);
    }

    private static ErrorMessage.ErrorMessageBuilder unauthorized(ErrorAttributes errorAttributes,
                                                                 HttpServletRequest request) {
        return ErrorMessage.builder()
                .errorCode(UnauthorizedException.UNAUTHORIZED_CODE)
                .errorMessage(UnauthorizedException.UNAUTHORIZED_MESSAGE);
    }

    private static ErrorMessage.ErrorMessageBuilder conflict(ErrorAttributes errorAttributes,
                                                             HttpServletRequest request) {
        return ErrorMessage.builder()
                .errorCode(ConflictException.CONFLICT_CODE)
                .errorMessage(ConflictException.CONFLICT_MESSAGE);
    }

    private static ErrorMessage.ErrorMessageBuilder badRequest(ErrorAttributes errorAttributes,
                                                               HttpServletRequest request) {
        return ErrorMessage.builder()
                .errorCode(BadRequestException.BAD_REQUEST_CODE)
                .errorMessage(BadRequestException.BAD_REQUEST_CODE);
    }

    private static ErrorMessage.ErrorMessageBuilder gatewayTimeout(ErrorAttributes errorAttributes,
                                                                   HttpServletRequest request) {
        return ErrorMessage.builder()
                .errorCode(GatewayTimeoutException.GATEWAY_TIMEOUT_CODE)
                .errorMessage(GatewayTimeoutException.GATEWAY_TIMEOUT_MESSAGE);
    }

    private static ErrorMessage.ErrorMessageBuilder notImplemented(ErrorAttributes errorAttributes,
                                                                   HttpServletRequest request) {
        return ErrorMessage.builder()
                .errorCode(NotImplementedException.NOT_IMPLEMENTED_CODE)
                .errorMessage(NotImplementedException.NOT_IMPEMENTED_MESSAGE);
    }

    private static ErrorMessage.ErrorMessageBuilder internalServerError(ErrorAttributes errorAttributes,
                                                                        HttpServletRequest request) {
        return ErrorMessage.builder()
                .errorCode(InternalServerErrorException.INTERNAL_SERVER_ERROR_CODE)
                .errorMessage(InternalServerErrorException.INTERNAL_SERVER_ERROR_MESSAGE);
    }

    private static ErrorMessage.ErrorMessageBuilder badGateway(ErrorAttributes errorAttributes,
                                                               HttpServletRequest request) {
        return ErrorMessage.builder()
                .errorCode(BadGatewayException.BAD_GATEWAY_CODE)
                .errorMessage(BadGatewayException.BAD_GATEWAY_MESSAGE);
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
                .errorCode("err.tech.wired.unknown-error")
                .errorMessage("An unknown error happened");
    }
}