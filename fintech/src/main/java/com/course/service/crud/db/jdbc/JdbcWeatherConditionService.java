package com.course.service.crud.db.jdbc;

import com.course.model.entity.WeatherCondition;
import com.course.repository.jdbc.WeatherConditionJdbcRepository;
import com.course.service.crud.db.contract.WeatherConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcWeatherConditionService implements WeatherConditionService {
    @Autowired
    WeatherConditionJdbcRepository weatherConditionJdbcRepository;

    @Override
    public void save(WeatherCondition weatherCondition) {
        if (weatherCondition.getId() != 0) {
            throw new IllegalArgumentException("The id must not be set for a new WeatherCondition");
        }

        weatherConditionJdbcRepository.save(weatherCondition);
    }

    @Override
    public WeatherCondition getById(int id) {
        return weatherConditionJdbcRepository.getById(id);
    }

    @Override
    public void update(WeatherCondition weatherCondition) {
        weatherConditionJdbcRepository.update(weatherCondition);
    }

    @Override
    public void deleteById(int id) {
        weatherConditionJdbcRepository.deleteById(id);
    }
}
