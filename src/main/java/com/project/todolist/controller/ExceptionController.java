package com.project.todolist.controller;

import com.project.todolist.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<AppError> httpClientErrorException(
            final HttpClientErrorException ex
    ) {
        return new ResponseEntity<>(
                new AppError(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

}
