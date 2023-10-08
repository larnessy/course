package com.course.model.weatherApi;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorInfo(@JsonProperty("code") Integer code, @JsonProperty("message") String message) { }
