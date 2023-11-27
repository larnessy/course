package com.course.facade;

import com.course.exception.myException.UnknownProblemWithResponse;
import com.course.model.entity.WeatherEntity;
import com.course.model.response.WeatherApiResponse;
import com.course.repository.WeatherCacheRepository;
import com.course.service.crud.db.jpa.JpaWeatherService;
import com.course.service.myRestClient.WeatherRestClient;
import com.course.service.processor.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeatherCache {

    private final WeatherCacheRepository weatherCacheRepository;
    private final WeatherRestClient myRestClient;
    private final JpaWeatherService jpaWeatherService;
    private final Converter converter;

    @Autowired
    public WeatherCache(WeatherCacheRepository weatherCacheRepository, WeatherRestClient myRestClient,
                        JpaWeatherService jpaWeatherService, Converter converter) {
        this.weatherCacheRepository = weatherCacheRepository;
        this.myRestClient = myRestClient;
        this.jpaWeatherService = jpaWeatherService;
        this.converter = converter;
    }

    public WeatherEntity get(String cityName) {
        WeatherEntity weatherEntity = weatherCacheRepository.get(cityName).orElse(null);

        if (weatherEntity == null) {
            // Можно сделать в начале метода, и добавление синхронизации в CacheRepository покажется избыточным,
            // по крайней мере такое впечатление возникнет на текущем этапе, однако CacheRepository реализован так
            // из соображения о том, что его потенциально могут использовать несколько сервисов (классов-фасадов).
            // Полагая, что кеш реализован для того, чтобы к нему часто успешно обращались, помещаю синхронизацию сюда.
            synchronized (this) {
                weatherEntity = jpaWeatherService.findTopByCityNameOrderByDateTimeDesc(cityName).orElse(null);
                if (weatherEntity == null || !weatherCacheRepository.add(weatherEntity)) {
                    weatherEntity = getWeatherEntity(cityName);
                    jpaWeatherService.insert(weatherEntity);
                    weatherCacheRepository.add(weatherEntity);
                }
            }
        }

        return weatherEntity;
    }

    private WeatherEntity getWeatherEntity(String cityName) {
        WeatherApiResponse weatherApiResponse = myRestClient.getCurrentWeather(cityName).getBody();
        if (weatherApiResponse == null) {
            throw new UnknownProblemWithResponse("Failed to get weather");
        }
        return converter.mapWeatherApiResponseToWeatherEntity(weatherApiResponse);
    }
}
