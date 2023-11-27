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

}
