package com.project.todolist.controller;

import com.project.todolist.AppError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<AppError> httpClientErrorException(
            final HttpClientErrorException ex
    ) {
        return new ResponseEntity<>(
                new AppError(ex.getStatusCode().value(), ex.getMessage()),
                ex.getStatusCode()
        );
    }
}
