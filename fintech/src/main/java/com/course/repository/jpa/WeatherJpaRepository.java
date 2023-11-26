package com.course.repository.jpa;

import com.course.model.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherJpaRepository extends JpaRepository<WeatherEntity, Integer> {
    public Optional<WeatherEntity> findTopByCityNameOrderByDateTimeDesc(String cityName);
}
