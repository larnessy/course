package com.course.service.crud.db.contract;

import com.course.model.entity.City;

import java.util.Optional;

public interface CityService {

    void insert(City city);

    Optional<City> getById(Integer id);

    void update(City city);

    void deleteById(Integer id);
}
