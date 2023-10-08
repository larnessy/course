package com.course.exception.myException;

import org.springframework.http.HttpStatus;

public class ThereIsNoCityWithThisId extends MyException {
    public ThereIsNoCityWithThisId(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
