package com.course.service.crud.db.jpa;

import com.course.model.entity.City;
import com.course.repository.jpa.CityJpaRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// for using Stub

class JpaCityServiceTest {

    @Test
    void save_successfulSave() {
        // given
        CityJpaRepository cityJpaRepository = mock(CityJpaRepository.class);
        JpaCityService cityService = new JpaCityService(cityJpaRepository);
        City city = new City("New York");

        // when
        assertEquals(null, city.getId());
        when(cityJpaRepository.save(any(City.class))).thenAnswer(invocation -> {
            City savedCity = invocation.getArgument(0);
            savedCity.setId(1);
            return savedCity;
        });
        cityService.insert(city);

        // then
        assertEquals(1, city.getId());
    }
}