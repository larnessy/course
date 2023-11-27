package com.course.exception.myException.db;

import com.course.exception.myException.MainExceptionForGlobalHandler;
import org.springframework.http.HttpStatus;

public class UnknownProblemWithDb extends MainExceptionForGlobalHandler {
    public UnknownProblemWithDb(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
