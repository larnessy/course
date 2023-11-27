package com.course.repository.jpa;

import com.course.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityJpaRepository extends JpaRepository<City, Integer> {
    public City findByName(String name);
}
