package com.api.normalizer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    /**
     * Handles the IllegalArgumentException and returns a ProblemDetail object representing a Bad Request status and error message.
     *
     * @param ex The IllegalArgumentException that needs to be handled.
     * @return A ProblemDetail object representing a Bad Request status and error message.
     */
    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail onIllegalArgumentException(IllegalArgumentException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
