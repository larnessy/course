package com.course.service.crud.db.contract;

import com.course.model.entity.WeatherEntity;

public interface WeatherService {
    void save(WeatherEntity weatherEntity);

    WeatherEntity getById(int id);

    void update(WeatherEntity weatherEntity);

    void deleteById(int id);
}
