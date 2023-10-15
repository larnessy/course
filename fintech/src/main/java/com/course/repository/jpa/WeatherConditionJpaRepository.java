package com.course.repository.jpa;

import com.course.model.entity.WeatherCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherConditionJpaRepository extends JpaRepository<WeatherCondition, Integer> {
}
