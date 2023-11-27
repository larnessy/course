package com.course.exception.myException;

import org.springframework.http.HttpStatus;

public class UnknownProblemWithResponse extends MainExceptionForGlobalHandler {
    public UnknownProblemWithResponse(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
