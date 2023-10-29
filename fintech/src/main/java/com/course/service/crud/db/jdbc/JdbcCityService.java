package com.course.service.crud.db.jdbc;

import com.course.model.entity.City;
import com.course.repository.jdbc.CityJdbcRepository;
import com.course.service.crud.db.contract.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcCityService implements CityService {

    @Autowired
    CityJdbcRepository cityJdbcRepository;

    @Override
    public void save(City city) {
        if (city.getId() != 0) {
            throw new IllegalArgumentException("The id must not be set for a new City");
        }

        cityJdbcRepository.save(city);
    }

    @Override
    public City getById(int id) {
        return cityJdbcRepository.getById(id);
    }

    @Override
    public void update(City city) {
        cityJdbcRepository.update(city);
    }

    @Override
    public void deleteById(int id) {
        cityJdbcRepository.deleteById(id);
    }
}
