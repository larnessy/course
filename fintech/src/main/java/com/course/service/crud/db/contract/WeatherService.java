package com.course.service.crud.db.contract;

import com.course.model.entity.WeatherEntity;

import java.util.Optional;

public interface WeatherService {
    void save(WeatherEntity weatherEntity);

    Optional<WeatherEntity> getById(int id);

    void update(WeatherEntity weatherEntity);

    void deleteById(int id);
}
