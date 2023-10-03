package com.course.repositorie;

import com.course.model.Weather;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// in case we need to use two or more controllers working with list (в DI не вижу смысла — только кода больше будет)
public class MyRepository {
    // data for test (new CopyOnWriteArrayList<>(Arrays.asList… — не эффективно, зато лаконично)
    public static final List<Weather> list = new CopyOnWriteArrayList<>(Arrays.asList(
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
                    LocalDateTime.of(2023, 9, 24, 3, 0, 0))));
}
