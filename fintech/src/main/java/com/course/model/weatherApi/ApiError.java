package com.course.model.weatherApi;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApiError(@JsonProperty("error") ErrorInfo error) { }
