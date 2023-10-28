package com.course.controller;

import com.course.model.Weather;
import com.course.service.crud.CRUDWeather;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

// http://localhost:8080/swagger-ui/index.html

@Tag(name = "Test", description = "API")
@RestController
@RequestMapping("/api/weather")
public class CRUDController {
    private final CRUDWeather crudWeather;

    public CRUDController(CRUDWeather crudWeather) {
        this.crudWeather = crudWeather;
    }

    @Operation(summary = "Find today's temperature in this city by his id")
    @GetMapping("/{city}")
    public double findTemperatureInCityToday(@PathVariable int city) {
        return crudWeather.findTemperatureInCityToday(city);
    }

    @Operation(summary = "Add city by adding weather with this city")
    @PostMapping("/")
    public void addCity(@RequestBody Weather weather) {
        crudWeather.addCity(weather);
    }

    @Operation(summary = "Save this city or update data by manipulate weather data")
    @PutMapping("/")
    public void saveOrUpdate(@RequestBody Weather weather) {
        crudWeather.saveOrUpdate(weather);
    }

    @Operation(summary = "Delete all weathers by id of city")
    @DeleteMapping("/{city}")
    public void deleteByCity(@PathVariable int city) {
        crudWeather.deleteByCity(city);
    }
}
