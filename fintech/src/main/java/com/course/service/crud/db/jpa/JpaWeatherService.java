package com.course.service.crud.db.jpa;

import com.course.model.entity.WeatherEntity;
import com.course.repository.jpa.WeatherJpaRepository;
import com.course.service.crud.db.contract.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JpaWeatherService implements WeatherService {

    @Autowired
    private WeatherJpaRepository weatherJpaRepository;

    @Override
    public void save(WeatherEntity weatherEntity) {
        if (weatherEntity.getId() != 0) {
            throw new IllegalArgumentException("The id must not be set for a new WeatherEntity");
        }

        weatherJpaRepository.save(weatherEntity);
    }

    @Override
    public WeatherEntity getById(int id) {
        return weatherJpaRepository.findById(id).orElse(null);
    }

    @Override
    public void update(WeatherEntity weatherEntity) {
        weatherJpaRepository.save(weatherEntity);
    }

    @Override
    public void deleteById(int id) {
        weatherJpaRepository.deleteById(id);
    }
}
