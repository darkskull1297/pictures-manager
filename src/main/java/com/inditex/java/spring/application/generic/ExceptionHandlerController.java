package com.inditex.java.spring.application.generic;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;
import java.util.*;

@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ExceptionHandlerController {
    private static final int UNAUTHORIZED = 401;
    private static final int BAD_REQUEST = 400;
    private static final int NOT_FOUND = 404;
    private static final int CONFLICT = 409;
    private static final int TOO_MANY_REQUESTS = 429;
    private static final int SERVICE_UNAVAILABLE = 503;

    /**
     * HttpClientErrorException handler, return rich information error.
     *
     * @param request a {@link HttpServletRequest} object.
     * @param ex      a {@link HttpClientErrorException} object.
     * @return a {@link ResponseEntity} object.
     */
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public ResponseEntity<ErrorsDTO> httpClientErrorHandler(HttpServletRequest request, HttpClientErrorException ex) {
        String errorCode = ex.getStatusCode().toString();
        switch (ex.getStatusCode().value()) {
            case UNAUTHORIZED:
                return error(ex, HttpStatus.UNAUTHORIZED, errorCode, ErrorDTO.LevelEnum.ERROR,
                        HttpStatus.UNAUTHORIZED.getReasonPhrase(), new Object[] { null });
            case BAD_REQUEST: {
                return error(ex, HttpStatus.BAD_REQUEST, errorCode, ErrorDTO.LevelEnum.ERROR,
                        HttpStatus.BAD_REQUEST.getReasonPhrase(), new Object[] { null });
            }
            case NOT_FOUND: {
                return error(ex, HttpStatus.NOT_FOUND, errorCode, ErrorDTO.LevelEnum.ERROR, HttpStatus.NOT_FOUND.getReasonPhrase(),
                        new Object[] { null });
            }
            case TOO_MANY_REQUESTS: {
                return error(ex, HttpStatus.TOO_MANY_REQUESTS, errorCode, ErrorDTO.LevelEnum.ERROR,
                        HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase(), new Object[] { null });
            }
            case SERVICE_UNAVAILABLE: {
                return error(ex, HttpStatus.SERVICE_UNAVAILABLE, errorCode, ErrorDTO.LevelEnum.ERROR,
                        HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(), new Object[] { null });
            }
            case CONFLICT: {
                return error(ex, HttpStatus.INTERNAL_SERVER_ERROR, errorCode, ErrorDTO.LevelEnum.ERROR, ex.getStatusText(),
                        new Object[] { null });
            }
            default: {
                return error(ex, HttpStatus.INTERNAL_SERVER_ERROR, errorCode, ErrorDTO.LevelEnum.ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), new Object[] { null });
            }
        }
    }

    /**
     * Constraint violation exception handler, return rich information error.
     *
     * @param ex a {@link ConstraintViolationException} object.
     * @return a {@link ResponseEntity} object.
     */
    @ExceptionHandler(value = { ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorsDTO> constraintValidationExceptionHandler(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> listErrors = ex.getConstraintViolations() == null ? new HashSet<>()
                : ex.getConstraintViolations();
        LOGGER.error(ex.getLocalizedMessage());
        String errorCode = Integer.toString(BAD_REQUEST);
        return error(ex, HttpStatus.BAD_REQUEST, errorCode, ErrorDTO.LevelEnum.FATAL, HttpStatus.BAD_REQUEST.getReasonPhrase(),
                listErrors.stream().map(x -> {
                    String result = "null";
                    if (x != null) {
                        result = x.getMessage();
                    }
                    return result;
                }).toArray());
    }

    /**
     * Entity Not Found exception handler, return rich information error.
     *
     * @param ex a {@link ConstraintViolationException} object.
     * @return a {@link ResponseEntity} object.
     */
    @ExceptionHandler(value = { EntityNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorsDTO> entityNotFoundExceptionHandler(EntityNotFoundException ex) {
        LOGGER.error(ex.getLocalizedMessage());
        ErrorDTO error = new ErrorDTO().code(Integer.toString(HttpStatus.NOT_FOUND.value()))
                .description(HttpStatus.NOT_FOUND.getReasonPhrase()).level(ErrorDTO.LevelEnum.ERROR)
                .message(ex.getLocalizedMessage());

        return new ResponseEntity<>(new ErrorsDTO().errors(Collections.singletonList(error)), HttpStatus.NOT_FOUND);
    }

    /**
     * Capture a methodArgumentNotValidException and returned rich information
     * error.
     *
     * @param ex a {@link MethodArgumentNotValidException} object.
     * @return a {@link ResponseEntity} object.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ErrorsDTO> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        LOGGER.error(ex.getLocalizedMessage());
        String errorCode = Integer.toString(BAD_REQUEST);
        return error(ex, HttpStatus.BAD_REQUEST, errorCode, ErrorDTO.LevelEnum.FATAL, HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getBindingResult().getAllErrors().stream().map(x -> {
                    if (x != null) {
                        return x.getDefaultMessage();
                    }
                    return "null";
                }).toArray());
    }

    /**
     * Capture when an Exception type exception is thrown and masks the information
     * returned.
     *
     * @param request a {@link HttpServletRequest} object.
     * @param ex      a {@link Exception} exception thrown
     * @return a {@link ResponseEntity} object.
     */
    @ExceptionHandler({ IOException.class, Exception.class })
    public ResponseEntity<ErrorsDTO> genericExceptionHandler(HttpServletRequest request, Exception ex) {
        if (request != null && ex != null) {
            LOGGER.error("Exception Occurred:: URL:{" + request.getRequestURL().toString() + "}, " + "class:{"
                    + ex.getClass() + "}," + " error:{" + ex.getLocalizedMessage() + "}");
        }
        String errorCode = Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value());
        LOGGER.error(ex == null ? "Exception null" : ex.getLocalizedMessage());
        ErrorDTO error = new ErrorDTO().code(errorCode).description(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .level(ErrorDTO.LevelEnum.ERROR).message(ex.getLocalizedMessage());

        return new ResponseEntity<>(new ErrorsDTO().errors(Collections.singletonList(error)),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * <p>
     * bindExceptionHandler.
     * </p>
     *
     * @param ex a {@link MethodArgumentNotValidException} object.
     * @return a {@link ResponseEntity} object.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorsDTO> bindExceptionHandler(BindException ex) {
        LOGGER.error(ex.getLocalizedMessage());
        String errorCode = Integer.toString(BAD_REQUEST);
        return error(ex, HttpStatus.BAD_REQUEST, errorCode, ErrorDTO.LevelEnum.FATAL, HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getBindingResult().getAllErrors().stream().map(x -> {
                    String result = "null";
                    if (x != null) {
                        result = x.getDefaultMessage();
                    }
                    return result;
                }).toArray());
    }

    /**
     * Constraint violation exception handler, return rich information error.
     *
     * @param matme a {@link MethodArgumentTypeMismatchException} object.
     * @return a {@link ResponseEntity} object.
     */
    @ExceptionHandler(value = { MethodArgumentTypeMismatchException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorsDTO> methodMethodArgumentTypeMismatchExceptionHandler(
            MethodArgumentTypeMismatchException matme) {

        String[] listErrors = { matme.getMessage() };

        LOGGER.error(matme.getLocalizedMessage());
        String errorCode = Integer.toString(BAD_REQUEST);
        return error(matme, HttpStatus.BAD_REQUEST, errorCode, ErrorDTO.LevelEnum.FATAL,
                HttpStatus.BAD_REQUEST.getReasonPhrase(), listErrors);
    }

    /**
     * Max upload size Handler for the files
     *
     * @param exc a {@link MaxUploadSizeExceededException} object.
     * @return a {@link ResponseEntity} object.
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorsDTO> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        String[] listErrors = { exc.getMessage() };

        LOGGER.error(exc.getLocalizedMessage());
        String errorCode = Integer.toString(HttpStatus.PAYLOAD_TOO_LARGE.value());
        return error(exc, HttpStatus.PAYLOAD_TOO_LARGE, errorCode, ErrorDTO.LevelEnum.FATAL,
                HttpStatus.PAYLOAD_TOO_LARGE.getReasonPhrase(), listErrors);
    }

    /**
     * Generate object response error with info like code, message, level and
     * description data.
     *
     * @param exception   a {@link Exception} object.
     * @param httpStatus  a {@link HttpStatus} object
     * @param code        a {@link String} object
     * @param level       a {@link String} object
     * @param description a {@link String} object
     * @param args        a {@link Object[]} object
     * @return a {@link ResponseEntity} object.
     */
    private ResponseEntity<ErrorsDTO> error(final Exception exception, final HttpStatus httpStatus, final String code,
                                            final ErrorDTO.LevelEnum level, final String description, Object[] args) {
        List<ErrorDTO> errors = new ArrayList<>();
        Arrays.stream(args).forEach(x -> errors.add(new ErrorDTO().code(code).description(description).level(level)
                .message(exception.getLocalizedMessage())));
        LOGGER.error(exception == null ? "Exception null" : exception.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorsDTO().errors(errors), httpStatus);
    }
}
