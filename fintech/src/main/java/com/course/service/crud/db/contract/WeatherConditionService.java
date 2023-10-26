package com.course.service.crud.db.contract;

import com.course.model.entity.WeatherCondition;

import java.util.Optional;

public interface WeatherConditionService {
    void save(WeatherCondition weatherCondition);

    Optional<WeatherCondition> getById(int id);

    void update(WeatherCondition weatherCondition);

    void deleteById(int id);
}
