package com.course.controller;

import com.course.model.entity.WeatherEntity;
import com.course.service.crud.db.jdbc.JdbcWeatherService;
import com.course.service.crud.db.jpa.JpaWeatherService;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// for test GET endpoint // weatherApi from 4 lesson // client are tested in this integration test

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class WeatherRestClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JdbcWeatherService jdbcWeatherService;

    @MockBean
    private JpaWeatherService jpaWeatherService;

    @SneakyThrows
    @Test
    void saveCurrentWeatherByJDBC_successfulSave_statusIsOkAndMethodSaveCalledOnce() {
        String cityName = "London";

        mockMvc.perform(get("/api/weather/test/jdbc/{cityName}", cityName))
                .andExpect(status().isOk());

        verify(jdbcWeatherService, times(1)).insert(any(WeatherEntity.class));
    }

    @SneakyThrows
    @Test
    void saveCurrentWeatherByJDBC_thereIsNoCityWithThisName_statusIsNotOkAndMethodSaveDidNotCall() {
        String cityName = "sadfsadfs";

        mockMvc.perform(get("/api/weather/test/jdbc/{cityName}", cityName))
                .andExpect(status().is4xxClientError());

        verifyNoInteractions(jdbcWeatherService);
    }

    @SneakyThrows
    @Test
    void saveCurrentWeatherByJPA_successfulSave_statusIsOkAndMethodSaveCalledOnce() {
        String cityName = "London";

        mockMvc.perform(get("/api/weather/test/jpa/{cityName}", cityName))
                .andExpect(status().isOk());

        verify(jpaWeatherService, times(1)).insert(any(WeatherEntity.class));
    }

    @SneakyThrows
    @Test
    void saveCurrentWeatherByJPA_thereIsNoCityWithThisName_statusIsNotOkAndMethodSaveDidNotCall() {
        String cityName = "sadfsadfs";

        mockMvc.perform(get("/api/weather/test/jpa/{cityName}", cityName))
                .andExpect(status().is4xxClientError());

        verifyNoInteractions(jpaWeatherService);
    }
}