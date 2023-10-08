package com.course.exception.myException;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class MyException extends RuntimeException {
    HttpStatus httpStatus;
    String message;

    public MyException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
