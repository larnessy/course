package com.course.service.crud.db.jdbc;

import com.course.model.entity.WeatherEntity;
import com.course.repository.jdbc.WeatherJdbcRepository;
import com.course.service.crud.db.contract.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcWeatherService implements WeatherService {
    @Autowired
    WeatherJdbcRepository weatherJdbcRepository;

    @Override
    public void save(WeatherEntity weatherEntity) {
        if (weatherEntity.getId() != 0) {
            throw new IllegalArgumentException("The id must not be set for a new WeatherEntity");
        }

        weatherJdbcRepository.save(weatherEntity);
    }

    @Override
    public WeatherEntity getById(int id) {
        return weatherJdbcRepository.getById(id);
    }

    @Override
    public void update(WeatherEntity weatherEntity) {
        weatherJdbcRepository.update(weatherEntity);
    }

    @Override
    public void deleteById(int id) {
        weatherJdbcRepository.deleteById(id);
    }
}
