package com.course.service.crud.db.jpa;

import com.course.model.entity.City;
import com.course.repository.jpa.CityJpaRepository;
import com.course.service.crud.db.contract.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JpaCityService implements CityService {

    @Autowired
    private CityJpaRepository cityJpaRepository;

    public void save(City city) {
        if (city.getId() != 0) {
            throw new IllegalArgumentException("The id must not be set for a new City");
        }

        cityJpaRepository.save(city);
    }

    @Override
    public City getById(int id) {
        return cityJpaRepository.findById(id).orElse(null);
    }

    @Override
    public void update(City city) {
        cityJpaRepository.save(city);
    }

    @Override
    public void deleteById(int id) {
        cityJpaRepository.deleteById(id);
    }
}
