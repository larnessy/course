package com.course.repository.jpa;

import com.course.model.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherJpaRepository extends JpaRepository<WeatherEntity, Integer> {
}
