package com.sungurovpavel.online_store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidPriceRangeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidPriceRange(InvalidPriceRangeException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IdMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIdMismatch(IdMismatchException ex) {
        return ex.getMessage();
    }

}
