package com.course.service.crud.db.jpa;

import com.course.model.entity.WeatherCondition;
import com.course.repository.jpa.WeatherConditionJpaRepository;
import com.course.service.crud.db.contract.WeatherConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JpaWeatherConditionService implements WeatherConditionService {

    @Autowired
    private WeatherConditionJpaRepository weatherConditionJpaRepository;

    @Override
    public void save(WeatherCondition weatherCondition) {
        if (weatherCondition.getId() != 0) {
            throw new IllegalArgumentException("The id must not be set for a new WeatherCondition");
        }

        weatherConditionJpaRepository.save(weatherCondition);
    }

    @Override
    public WeatherCondition getById(int id) {
        return weatherConditionJpaRepository.findById(id).orElse(null);
    }

    @Override
    public void update(WeatherCondition weatherCondition) {
        weatherConditionJpaRepository.save(weatherCondition);
    }

    @Override
    public void deleteById(int id) {
        weatherConditionJpaRepository.deleteById(id);
    }
}
