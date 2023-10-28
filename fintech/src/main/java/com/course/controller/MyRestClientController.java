package com.course.controller;

import com.course.service.myRestClient.MyRestClient;
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
public class MyRestClientController {
    private final MyRestClient myRestClient;


    public MyRestClientController(MyRestClient myRestClient) {
        this.myRestClient = myRestClient;
    }

    @Operation(summary = "Get current weather")
    @GetMapping("/{cityName}")
    public ResponseEntity<String> getCurrentWeather(@PathVariable String cityName) {
        return myRestClient.getCurrentWeather(cityName);
    }
}
