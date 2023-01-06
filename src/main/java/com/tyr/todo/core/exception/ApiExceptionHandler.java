package com.tyr.todo.core.exception;

import com.tyr.todo.core.model.ApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException exception) {
        return new ResponseEntity<>(
                ApiExceptionResponse.builder()
                        .errorCode(exception.getErrorCode())
                        .errorMessage(exception.getErrorMessage())
                        .zonedDateTime(ZonedDateTime.now())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
