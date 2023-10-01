package com.course.entity;

import lombok.*;
import java.time.LocalDateTime;

// maybe must add Bean or other annotation
@Value
public class Weather {
    int id;
    String nameOfRegion;
    double temperature;
    LocalDateTime dateTime;
}
