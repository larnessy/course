package com.course.exception.myException;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class MainExceptionForGlobalHandler extends RuntimeException {
    HttpStatus httpStatus;
    String message;

    public MainExceptionForGlobalHandler(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
