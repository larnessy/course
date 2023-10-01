package com.course.service;

import com.course.model.Weather;

import java.util.*;
import java.util.stream.Collectors;

public class WeatherProcessorImpl implements WeatherProcessor {
    @Override
    public Map<String, Double> mapAverageTemperatureByRegion(List<Weather> list) {
        return list.stream()
                .collect(Collectors.groupingBy(Weather::getNameOfRegion,
                        Collectors.averagingDouble(Weather::getTemperature)));
    }

    @Override
    public Set<String> findRegionsWithTemperatureAboveThan(List<Weather> list, double temperature) {
        return list.stream()
                .filter(w -> w.getTemperature() > temperature)
                .map(Weather::getNameOfRegion)
                .collect(Collectors.toSet());
    }

    @Override
    public Map<Integer, List<Double>> mapTemperaturesById(List<Weather> list) {
        return list.stream()
                .collect(Collectors.groupingBy(Weather::getId,
                        Collectors.mapping(Weather::getTemperature, Collectors.toList())));
    }

    @Override
    public Map<Double, List<Weather>> mapWeatherByTemperature(List<Weather> list) {
        return list.stream()
                .collect(Collectors.groupingBy(Weather::getTemperature, Collectors.toList()));
    }
}
