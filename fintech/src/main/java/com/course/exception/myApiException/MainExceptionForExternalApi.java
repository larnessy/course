package com.course.exception.myApiException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MainExceptionForExternalApi extends RuntimeException {

    private final int errorCode ;
}
