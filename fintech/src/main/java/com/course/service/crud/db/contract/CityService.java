package com.course.service.crud.db.contract;

import com.course.model.entity.City;

import java.util.Optional;

public interface CityService {

    void save(City city);

    Optional<City> getById(int id);

    void update(City city);

    void deleteById(int id);
}
