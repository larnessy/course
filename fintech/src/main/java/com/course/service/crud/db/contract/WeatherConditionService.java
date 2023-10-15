package com.course.service.crud.db.contract;

import com.course.model.entity.WeatherCondition;

public interface WeatherConditionService {
    void save(WeatherCondition weatherCondition);

    WeatherCondition getById(int id);

    void update(WeatherCondition weatherCondition);

    void deleteById(int id);
}
