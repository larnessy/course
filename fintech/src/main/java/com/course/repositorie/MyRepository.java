package com.course.repositorie;

import com.course.model.Weather;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

// in case we need to use two or more controllers (в DI не вижу смысла — только кода больше будет)
public class Repository {
    // data for test
    public static final List<Weather> list = Arrays.asList(
            new Weather(1, "region1", 1, LocalDateTime.now()),
            new Weather(2, "region2", 2, LocalDateTime.now()),
            new Weather(3, "region3", 3, LocalDateTime.now()),
            new Weather(4, "region4", 4, LocalDateTime.now()),
            new Weather(5, "region5", 5, LocalDateTime.now()),
            new Weather(5, "region5", 6,
                    LocalDateTime.of(2023, 9, 24, 1, 0, 0)),
            new Weather(5, "region5", 7,
                    LocalDateTime.of(2023, 9, 24, 2, 0, 0)),
            new Weather(5, "region5", 7,
                    LocalDateTime.of(2023, 9, 24, 3, 0, 0)));
}
