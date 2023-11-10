package com.course.service.crud.db.contract;

import com.course.model.entity.WeatherCondition;

import java.util.Optional;

public interface WeatherConditionService {
    void insert(WeatherCondition weatherCondition);

    Optional<WeatherCondition> getById(Integer id);

    void update(WeatherCondition weatherCondition);

    void deleteById(Integer id);
}
