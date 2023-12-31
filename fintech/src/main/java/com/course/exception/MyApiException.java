package com.course.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyApiException extends RuntimeException {

    private final int errorCode ;
}
