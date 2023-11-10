package com.course.service.crud.db.contract;

import com.course.model.entity.WeatherEntity;

import java.util.Optional;

public interface WeatherService {
    void insert(WeatherEntity weatherEntity);

    Optional<WeatherEntity> getById(Integer id);

    void update(WeatherEntity weatherEntity);

    void deleteById(Integer id);
}
