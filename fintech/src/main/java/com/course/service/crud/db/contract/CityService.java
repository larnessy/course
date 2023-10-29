package com.course.service.crud.db.contract;

import com.course.model.entity.City;

public interface CityService {

    void save(City city);

    City getById(int id);

    void update(City city);

    void deleteById(int id);
}
