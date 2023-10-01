package com.course.exception.handler;

import com.course.exception.data.AdditionalExceptionData;
import com.course.exception.myException.ThereIsAlreadySuchWeather;
import com.course.exception.myException.ThereIsNoCityWithThisId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class WeatherGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AdditionalExceptionData> handleException(NoSuchElementException exception) {
        AdditionalExceptionData data = new AdditionalExceptionData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ThereIsAlreadySuchWeather.class)
    public ResponseEntity<AdditionalExceptionData> handleException(ThereIsAlreadySuchWeather exception) {
        AdditionalExceptionData data = new AdditionalExceptionData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ThereIsNoCityWithThisId.class)
    public ResponseEntity<AdditionalExceptionData> handleException(ThereIsNoCityWithThisId exception) {
        AdditionalExceptionData data = new AdditionalExceptionData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<AdditionalExceptionData> handleException(Exception exception) {
        AdditionalExceptionData data = new AdditionalExceptionData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
