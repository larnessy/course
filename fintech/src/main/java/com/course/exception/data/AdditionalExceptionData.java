package com.course.exception.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdditionalExceptionData {
    private String info;

    public AdditionalExceptionData(String info) {
        this.info = info;
    }
}
