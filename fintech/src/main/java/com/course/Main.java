package com.course;

import com.course.model.entity.City;
import com.course.model.entity.WeatherCondition;
import com.course.model.entity.WeatherEntity;
import com.course.service.crud.db.jdbc.JdbcCityService;
import com.course.service.crud.db.jdbc.JdbcWeatherConditionService;
import com.course.service.crud.db.jdbc.JdbcWeatherService;
import com.course.service.crud.db.jpa.JpaCityService;
import com.course.service.crud.db.jpa.JpaWeatherConditionService;
import com.course.service.crud.db.jpa.JpaWeatherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(JpaCityService jpaCityService,
                                               JpaWeatherConditionService jpaWeatherConditionService,
                                               JpaWeatherService jpaWeatherService,
                                               JdbcCityService jdbcCityService,
                                               JdbcWeatherConditionService jdbcWeatherConditionService,
                                               JdbcWeatherService jdbcWeatherService) {
        return args -> {

            // JpaCityService
            City city = new City("Dallas");

            jpaCityService.save(city);
            assert city.equals(jpaCityService.getById(city.getId()).orElse(null));
            jpaCityService.update(new City(3, "Atlanta"));
            jpaCityService.deleteById(1);

            // JpaWeatherConditionService
            WeatherCondition weatherCondition = new WeatherCondition("Foggy");

            jpaWeatherConditionService.save(weatherCondition);
            assert weatherCondition.equals(jpaWeatherConditionService
                    .getById(weatherCondition.getId()).orElse(null));
            jpaWeatherConditionService.update(new WeatherCondition(3, "Clear"));
            jpaWeatherConditionService.deleteById(1);

            // JpaWeatherService
            WeatherEntity weatherEntity1 = new WeatherEntity(city, weatherCondition, 3, LocalDateTime.now());
            WeatherEntity weatherEntity2 = new WeatherEntity(city, weatherCondition, 4, LocalDateTime.now());

            jpaWeatherService.save(weatherEntity1);
            jpaWeatherService.save(weatherEntity2);
            assert weatherEntity1.equals(jpaWeatherService.getById(weatherEntity1.getId()).orElse(null));
            jpaWeatherService.update(new WeatherEntity(2, city, weatherCondition, 6, LocalDateTime.now()));
            jpaWeatherService.deleteById(1);


            // JdbcCityService
            City city2 = new City("Tomsk");

            jdbcCityService.save(city2);
            assert city.equals(jdbcCityService.getById(city2.getId()).orElse(null));
            jdbcCityService.update(new City(4, "Paris"));
            jdbcCityService.deleteById(2);

            // JdbcWeatherConditionService
            WeatherCondition weatherCondition2 = new WeatherCondition("Rain");

            jdbcWeatherConditionService.save(weatherCondition2);
            assert weatherCondition2.equals(jdbcWeatherConditionService
                    .getById(weatherCondition2.getId()).orElse(null));
            jdbcWeatherConditionService.update(new WeatherCondition(4, "Haboob"));
            jdbcWeatherConditionService.deleteById(2);

            // JdbcWeatherService
            WeatherEntity weatherEntity3 = new WeatherEntity(city2,
                    weatherCondition2, 5, LocalDateTime.now());
            WeatherEntity weatherEntity4 = new WeatherEntity(city2,
                    weatherCondition2, 6, LocalDateTime.now());

            jdbcWeatherService.save(weatherEntity3);
            jdbcWeatherService.save(weatherEntity4);
            assert weatherEntity3.equals(jdbcWeatherService
                    .getById(weatherEntity3.getId()).orElse(null));
            jdbcWeatherService.update(new WeatherEntity(4,
                    city2, weatherCondition2, 8, LocalDateTime.now()));
            jdbcWeatherService.deleteById(3);

            // cascade

//            jdbcCityService.deleteById(6);
//            jpaCityService.deleteById(7);
        };
    }
}
