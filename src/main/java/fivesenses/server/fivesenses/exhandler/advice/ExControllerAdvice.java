package fivesenses.server.fivesenses.exhandler.advice;

import fivesenses.server.fivesenses.dto.Meta;
import fivesenses.server.fivesenses.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestControllerAdvice()
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Result<Object> entityNotFoundExHandler(EntityNotFoundException e) {
        log.error("[exceptionHandler] ex", e);

        return new Result<>(new Meta(e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public Result<Object> illegalStateExHandler(IllegalStateException e) {
        log.error("[exceptionHandler] ex", e);
        return new Result<>(new Meta(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Object> illegalArgumentExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new Result<>(new Meta(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Object> httpMessageNotReadableExHandler(HttpMessageNotReadableException e) {
        log.error("[exceptionHandler] ex", e);

        return new Result<>(new Meta(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public Result<Object> badCredentialsExceptionExHandler(BadCredentialsException e) {
        log.error("[exceptionHandler] ex", e);

        return new Result<>(new Meta(e.getMessage(), HttpStatus.UNAUTHORIZED.value()));
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result<Object> exceptionExHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);

        return new Result<>(new Meta(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
