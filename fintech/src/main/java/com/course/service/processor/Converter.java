package com.course.service.processor;

import com.course.model.entity.City;
import com.course.model.entity.WeatherCondition;
import com.course.model.entity.WeatherEntity;
import com.course.model.response.Current;
import com.course.model.response.Location;
import com.course.model.response.WeatherApiResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class Converter {
    public WeatherEntity mapWeatherApiResponseToWeatherEntity(WeatherApiResponse weatherApiResponse) {
        Current current= weatherApiResponse.getCurrent();
        Location location = weatherApiResponse.getLocation();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m");
        LocalDateTime localDateTime = LocalDateTime.parse(location.getLocaltime(), formatter);
        return new WeatherEntity(new City(location.getName()),
                new WeatherCondition(current.getCondition().getName()),
                current.getTemperatureCelsius(), localDateTime);
    }
}
