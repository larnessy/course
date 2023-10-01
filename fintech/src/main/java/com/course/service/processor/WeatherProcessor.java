package com.course.service;

import com.course.model.Weather;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WeatherProcessor {
    Map<String, Double> mapAverageTemperatureByRegion(List<Weather> list);

    Set<String> findRegionsWithTemperatureAboveThan(List<Weather> list, double temperature);

    Map<Integer, List<Double>> mapTemperaturesById(List<Weather> list);

    Map<Double, List<Weather>> mapWeatherByTemperature(List<Weather> list);
}
