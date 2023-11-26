package com.course.service.crud.db.jpa;

import com.course.model.entity.City;
import com.course.model.entity.WeatherEntity;
import com.course.repository.jpa.CityJpaRepository;
import com.course.repository.jpa.WeatherConditionJpaRepository;
import com.course.repository.jpa.WeatherJpaRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JpaWeatherServiceTest {

    @Test
    void findTopByCityNameOrderByDateTimeDesc_successful_cityIdInitialized() {
        WeatherJpaRepository weatherJpaRepository = mock(WeatherJpaRepository.class);
        CityJpaRepository cityJpaRepository = mock(CityJpaRepository.class);
        WeatherConditionJpaRepository weatherConditionJpaRepository = mock(WeatherConditionJpaRepository.class);
        JpaWeatherService jpaWeatherService = new JpaWeatherService(weatherJpaRepository,
                cityJpaRepository, weatherConditionJpaRepository);
        City city = new City("Los Angeles");

        assertNull(city.getId());

        when(weatherJpaRepository.findTopByCityNameOrderByDateTimeDesc(any(String.class)))
                .thenAnswer(invocation -> {
            String cityName = invocation.getArgument(0);
            WeatherEntity weatherEntity = new WeatherEntity();
            weatherEntity.setCity(new City(2, cityName));
            return Optional.of(weatherEntity);
        });

        WeatherEntity weatherEntity = jpaWeatherService
                .findTopByCityNameOrderByDateTimeDesc(city.getName()).orElse(null);

        assertEquals(weatherEntity.getCity().getName(), city.getName());
        assertEquals(2, weatherEntity.getCity().getId());
    }
}