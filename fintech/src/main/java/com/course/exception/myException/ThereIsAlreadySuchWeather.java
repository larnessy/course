package com.course.exception.myException;

public class ThereIsAlreadySuchWeather extends RuntimeException {
    public ThereIsAlreadySuchWeather(String message) {
        super(message);
    }
}
