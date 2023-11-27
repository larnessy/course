package com.course.controller;

import com.course.model.entity.WeatherEntity;
import com.course.service.crud.db.jdbc.JdbcWeatherService;
import com.course.service.crud.db.jpa.JpaWeatherService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// for test GET endpoint // weatherApi from 4 lesson // client are tested in this integration test

@SpringBootTest
@AutoConfigureMockMvc
class WeatherRestClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JdbcWeatherService jdbcWeatherService;

    @MockBean
    private JpaWeatherService jpaWeatherService;

    @SneakyThrows
    @Test
    void saveCurrentWeatherByJDBC_successfulSave_statusIsOkAndMethodSaveCalledOnceAndResponseContractWasNotChanged() {
        String cityName = "London";

        mockMvc.perform(get("/api/weather/test/jdbc/{cityName}", cityName))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(jdbcWeatherService, times(1)).insert(any(WeatherEntity.class));
    }

    @SneakyThrows
    @Test
    void saveCurrentWeatherByJDBC_thereIsNoCityWithThisName_statusIsNotOkAndMethodSaveDidNotCallAndContractWasNotChanged() {
        String cityName = "sadfsadfs";

        mockMvc.perform(get("/api/weather/test/jdbc/{cityName}", cityName))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .string("{\"info\":\"No location found matching parameter 'city'\"}"));

        verifyNoInteractions(jdbcWeatherService);
    }

    @SneakyThrows
    @Test
    void saveCurrentWeatherByJPA_successfulSave_statusIsOkAndMethodSaveCalledOnceAndResponseContractWasNotChanged() {
        String cityName = "London";

        mockMvc.perform(get("/api/weather/test/jpa/{cityName}", cityName))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(jpaWeatherService, times(1)).insert(any(WeatherEntity.class));
    }

    @SneakyThrows
    @Test
    void saveCurrentWeatherByJPA_thereIsNoCityWithThisName_statusIsNotOkAndMethodSaveDidNotCallAndContractWasNotChanged() {
        String cityName = "sadfsadfs";

        mockMvc.perform(get("/api/weather/test/jpa/{cityName}", cityName))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .string("{\"info\":\"No location found matching parameter 'city'\"}"));

        verifyNoInteractions(jpaWeatherService);
    }
}