package com.course.exception.myException;

import org.springframework.http.HttpStatus;

public class ThereIsAlreadySuchWeather extends MyException {
    public ThereIsAlreadySuchWeather(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
