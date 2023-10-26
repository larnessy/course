package com.course.exception.handler;

import com.course.exception.data.AdditionalExceptionData;
import com.course.exception.myException.MainExceptionForGlobalHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class WeatherGlobalExceptionHandler {
    @ExceptionHandler(MainExceptionForGlobalHandler.class)
    public ResponseEntity<AdditionalExceptionData> handleException(MainExceptionForGlobalHandler exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(new AdditionalExceptionData(exception.getMessage()));
    }

    // further not custom exception

    @ExceptionHandler
    public ResponseEntity<AdditionalExceptionData> handleException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new AdditionalExceptionData(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<AdditionalExceptionData> handleException(NoSuchElementException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new AdditionalExceptionData(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<AdditionalExceptionData> handleException(Throwable exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AdditionalExceptionData("Unknown server error"));
    }
}
