package com.course.exception.myException;

import org.springframework.http.HttpStatus;

public class ThereIsNoCityWithThisId extends MainExceptionForGlobalHandler {
    public ThereIsNoCityWithThisId(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
