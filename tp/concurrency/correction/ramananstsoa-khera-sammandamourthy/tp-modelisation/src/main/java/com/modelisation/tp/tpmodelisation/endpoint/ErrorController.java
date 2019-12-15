package com.modelisation.tp.tpmodelisation.endpoint;

import com.modelisation.tp.tpmodelisation.exception.ConflictException;
import com.modelisation.tp.tpmodelisation.exception.ForbiddenException;
import com.modelisation.tp.tpmodelisation.dto.ErrorMessage;
import com.modelisation.tp.tpmodelisation.dto.ErrorMessages;
import com.modelisation.tp.tpmodelisation.exception.InternalServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.BiFunction;

import static com.modelisation.tp.tpmodelisation.dto.ErrorMessageType.FUNCTIONAL;
import static com.modelisation.tp.tpmodelisation.dto.ErrorMessageType.TECHNICAL;
import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 *
 * Surcharge le comportement par défaut de la gestion des erreurs
 */
@Slf4j
@RestController
@ConditionalOnWebApplication(type = SERVLET)
public class ErrorController extends AbstractErrorController {

    private static final String PATH = "/error";
    private static final List<HttpStatus> HTTP_STATUS_FUNCTIONAL_ERROR = Arrays.asList(NOT_FOUND, UNAUTHORIZED, FORBIDDEN, CONFLICT, INTERNAL_SERVER_ERROR);
    private static final Map<HttpStatus, BiFunction<ErrorAttributes, HttpServletRequest, ErrorMessage.ErrorMessageBuilder>> MESSAGES;

    static {
        Map<HttpStatus, BiFunction<ErrorAttributes, HttpServletRequest, ErrorMessage.ErrorMessageBuilder>> messages =
                new EnumMap<>(HttpStatus.class);
        messages.put(NOT_FOUND, ErrorController::notFound);
        messages.put(UNAUTHORIZED, ErrorController::unauthorized);
        messages.put(FORBIDDEN, ErrorController::forbidden);
        messages.put(CONFLICT, ErrorController::conflict);
        messages.put(INTERNAL_SERVER_ERROR, ErrorController::server);
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
                .errorCode("err.func.wired.not-found")
                .errorMessage("The specified url does not exist");
    }

    // Don't warn about unused parameters, required for mapping to a BiConsumer
    @SuppressWarnings("squid:S1172")
    private static ErrorMessage.ErrorMessageBuilder unauthorized(ErrorAttributes errorAttributes,
                                                                 HttpServletRequest request) {
        return ErrorMessage.builder()
                .errorCode("err.func.wired.unauthorized")
                .errorMessage("The access is unauthorized");
    }

    // Don't warn about unused parameters, required for mapping to a BiConsumer
    @SuppressWarnings("squid:S1172")
    private static ErrorMessage.ErrorMessageBuilder forbidden(ErrorAttributes errorAttributes,
                                                              HttpServletRequest request) {
        return ErrorMessage.builder()
                .errorCode(ForbiddenException.FORBIDDEN_CODE)
                .errorMessage(ForbiddenException.FORBIDDEN_MESSAGE);
    }

    // Don't warn about unused parameters, required for mapping to a BiConsumer
    @SuppressWarnings("squid:S1172")
    private static ErrorMessage.ErrorMessageBuilder conflict(ErrorAttributes errorAttributes,
                                                             HttpServletRequest request) {
        return ErrorMessage.builder()
                .errorCode(ConflictException.CONFLICT_CODE)
                .errorMessage(ConflictException.CONFLICT_MESSAGE);
    }

    // Don't warn about unused parameters, required for mapping to a BiConsumer
    @SuppressWarnings("squid:S1172")
    private static ErrorMessage.ErrorMessageBuilder server (ErrorAttributes errorAttributes,
                                                             HttpServletRequest request) {
        return ErrorMessage.builder()
                .errorCode(InternalServerException.INTERNAL_SERVER_CODE)
                .errorMessage(InternalServerException.INTERNAL_SERVER_MESSAGE);
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
