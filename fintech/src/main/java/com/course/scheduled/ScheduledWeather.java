package com.course.scheduled;

import com.course.model.entity.WeatherEntity;
import com.course.model.response.WeatherApiResponse;
import com.course.service.myRestClient.WeatherRestClient;
import com.course.service.processor.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
public class ScheduledWeather {
    private final KafkaTemplate<String, WeatherEntity> kafkaTemplate;
    private final WeatherRestClient weatherRestClient;
    private final Converter converter;
    private final String[] cities;
    private int currentIndex = -1;

    @Autowired
    ScheduledWeather(KafkaTemplate<String, WeatherEntity> kafkaTemplate,
                     WeatherRestClient weatherRestClient,
                     Converter converter,
                     @Qualifier("cityNames") String[] cities) {
        this.kafkaTemplate = kafkaTemplate;
        this.weatherRestClient = weatherRestClient;
        this.converter = converter;
        this.cities = cities;

    }

    // с небольшим запасом
    @Scheduled(cron = "0 */4 * * * *")
    public void getAndSendToTopicWeatherEntity() {
        currentIndex = (currentIndex + 1) % cities.length;
        try {
            WeatherApiResponse weatherApiResponse = weatherRestClient.getCurrentWeather(cities[currentIndex]).getBody();
            assert weatherApiResponse != null;
            WeatherEntity weatherEntity = converter.mapWeatherApiResponseToWeatherEntity(weatherApiResponse);
            kafkaTemplate.send("demo_topic", weatherEntity);
        } catch (Exception exception) {
            log.warn("Не удалось получить актуальную информацию о погоде в городе: " + cities[currentIndex]);
        }
    }
}
