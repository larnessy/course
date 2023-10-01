package com.course.exception.myException;

public class ThereIsNoCityWithThisId extends RuntimeException {
    public ThereIsNoCityWithThisId(String message) {
        super(message);
    }
}
