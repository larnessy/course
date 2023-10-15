package com.course.exception.handler;

import com.course.exception.myApiException.MyApiException;
import com.course.exception.data.AdditionalExceptionData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WeatherApiHandler {
    @ExceptionHandler(MyApiException.class)
    public ResponseEntity<AdditionalExceptionData> handleException(MyApiException exception) {
        ResponseEntity<AdditionalExceptionData> response;
        // errorCodes в этом API довольно мало, так что, думается мне, такой способ оптимален
        switch (exception.getErrorCode()) {
            case 1002 -> response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AdditionalExceptionData("API key not provided"));
            case 1003 -> response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AdditionalExceptionData("Parameter 'city' not provided"));
            case 1006 -> response = ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AdditionalExceptionData("No location found matching parameter 'city'"));
            default -> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AdditionalExceptionData("Unknown error")); // 1005 here
        }
        return response;
    }
}
