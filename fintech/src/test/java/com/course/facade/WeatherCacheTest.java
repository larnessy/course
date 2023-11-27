package com.course.facade;

import com.course.model.entity.City;
import com.course.model.entity.WeatherCondition;
import com.course.model.entity.WeatherEntity;
import com.course.repository.WeatherCacheRepository;
import com.course.service.crud.db.jpa.JpaWeatherService;
import com.course.service.myRestClient.WeatherRestClient;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class WeatherCacheTest {
    @SpyBean
    private WeatherCache weatherCache;

    @SpyBean
    private WeatherCacheRepository weatherCacheRepository;

    @SpyBean
    private JpaWeatherService jpaWeatherService;

    @SpyBean
    private WeatherRestClient weatherRestClient;

    @AfterEach
    public void clear(){
        weatherCacheRepository.clear();
    }

    @Test
    void testGet_weatherInCacheIsExpired_deleteExpiredValueAndAddNewValueToCache() {
        String cityName = "Chelyabinsk";
        WeatherEntity weatherEntity = new WeatherEntity(new City(cityName),
                new WeatherCondition(), 20, LocalDateTime.now());
        weatherCacheRepository.add(weatherEntity);
        weatherEntity.setDateTime(LocalDateTime.now().minusMinutes(20));
        int countBefore = weatherCacheRepository.size();

        WeatherEntity result = weatherCache.get(cityName);

        assertNotNull(result);
        assertNotEquals(result.getDateTime(), weatherEntity.getDateTime());
        assertEquals(countBefore, weatherCacheRepository.size());
        verify(jpaWeatherService, times(1)).findTopByCityNameOrderByDateTimeDesc(cityName);
        verify(jpaWeatherService, times(1)).insert(any());
        verify(weatherRestClient, times(1)).getCurrentWeather(any());
    }

    @Test
    void testGet_cacheHit_thereWereNoCallsMethodsExceptCacheMethods() {
        weatherCacheRepository.add(new WeatherEntity(new City("ExistingCity"),
                new WeatherCondition(), 20, LocalDateTime.now()));
        String cityName = "ExistingCity";

        WeatherEntity result = weatherCache.get(cityName);

        assertNotNull(result);
        assertEquals(cityName, result.getCity().getName());
        verify(jpaWeatherService, never()).findTopByCityNameOrderByDateTimeDesc(cityName);
        verify(jpaWeatherService, never()).insert(any());
        verify(weatherRestClient, never()).getCurrentWeather(any());
    }

    @Test
    void testGet_cacheMissAndDatabaseMiss_clientCallAndAddValueToDatabaseAndCache() {
        String cityName = "Chelyabinsk";
        int countBefore = weatherCacheRepository.size();

        WeatherEntity weatherEntity = weatherCache.get(cityName);

        assertNotNull(weatherEntity);
        assertEquals(countBefore + 1, weatherCacheRepository.size());
        verify(jpaWeatherService, times(1)).findTopByCityNameOrderByDateTimeDesc(cityName);
        verify(jpaWeatherService, times(1)).insert(any());
        verify(weatherRestClient, times(1)).getCurrentWeather(any());
        verify(weatherCacheRepository, atLeast(1)).add(any());
    }
}