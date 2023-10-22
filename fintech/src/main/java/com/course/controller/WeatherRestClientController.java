package com.course.controller;

import com.course.exception.myException.UnknownProblemWithResponse;
import com.course.model.entity.WeatherEntity;
import com.course.model.response.WeatherApiResponse;
import com.course.service.crud.db.jdbc.JdbcCityService;
import com.course.service.crud.db.jdbc.JdbcWeatherService;
import com.course.service.crud.db.jpa.JpaCityService;
import com.course.service.crud.db.jpa.JpaWeatherService;
import com.course.service.myRestClient.WeatherRestClient;
import com.course.service.processor.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// http://localhost:8080/swagger-ui/index.html

@Tag(name = "SecondTest", description = "External API")
@RestController
@RequestMapping("/api/weather/test")
public class WeatherRestClientController {
    private final WeatherRestClient myRestClient;
    private final JpaWeatherService jpaWeatherService;
    private final JdbcWeatherService jdbcWeatherService;
    private final Converter converter;
    private final JpaCityService jpaCityService;
    private final JdbcCityService jdbcCityService;


    public WeatherRestClientController(WeatherRestClient myRestClient,
                                       JpaWeatherService jpaWeatherService,
                                       JdbcWeatherService jdbcWeatherService,

                                       JpaCityService jpaCityService,
                                       JdbcCityService jdbcCityService,

                                       Converter converter) {
        this.myRestClient = myRestClient;
        this.jpaWeatherService = jpaWeatherService;
        this.jdbcWeatherService = jdbcWeatherService;
        this.converter = converter;

        this.jpaCityService = jpaCityService;
        this.jdbcCityService = jdbcCityService;
    }

    @Operation(summary = "Get current weather")
    @GetMapping("/{cityName}")
    public ResponseEntity<WeatherApiResponse> getCurrentWeather(@PathVariable String cityName) {
        return myRestClient.getCurrentWeather(cityName);
    }

    @Operation(summary = "Save current weather by JDBC")
    @GetMapping("/jdbc/{cityName}")
    public void saveCurrentWeatherByJDBC(@PathVariable String cityName) {
        jdbcWeatherService.save(getWeatherEntity(cityName));
    }

    @Operation(summary = "Save current weather by JPA")
    @GetMapping("/jpa/{cityName}")
    public void saveCurrentWeatherByJPA(@PathVariable String cityName) {
        jpaWeatherService.save(getWeatherEntity(cityName));
    }

    // for test
    @Operation(summary = "Save current city by JPA")
    @GetMapping("/city/jpa/{cityName}")
    public void saveCurrentCityByJPA(@PathVariable String cityName) {
        jpaCityService.save(getWeatherEntity(cityName).getCity());
    }

    // for test
    @Operation(summary = "Save current city by JDBC")
    @GetMapping("/city/jdbc/{cityName}")
    public void saveCurrentCityByJDBC(@PathVariable String cityName) {
        jdbcCityService.save(getWeatherEntity(cityName).getCity());
    }

    public WeatherEntity getWeatherEntity(String cityName) {
        WeatherApiResponse weatherApiResponse = myRestClient.getCurrentWeather(cityName).getBody();
        if(weatherApiResponse == null) {
            throw new UnknownProblemWithResponse("Failed to get weather");
        }
        return converter.mapWeatherApiResponseToWeatherEntity(weatherApiResponse);
    }

}
