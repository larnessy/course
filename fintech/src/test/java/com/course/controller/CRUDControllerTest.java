package com.course.controller;

import com.course.model.Weather;
import com.course.service.crud.CRUDWeather;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// for test POST endpoint

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CRUDControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CRUDWeather crudWeather;

    @SneakyThrows
    @Test
    void addCity_successfulAddCity_statusIsOkAndMethodAddCityCalledOnce() {
        Weather weather = new Weather(100, "London", 10, LocalDateTime.now());

        mockMvc.perform(post("/api/weather/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(weather))
        ).andExpect(status().isOk());

        verify(crudWeather, times(1)).addCity(weather);
    }

    @SneakyThrows
    @Test
    void addCity_notRightDataType_statusIsNotOkAndMethodAddCityDidNotCall() {
        String empty = "";

        mockMvc.perform(post("/api/weather/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(empty)
        ).andExpect(status().is5xxServerError());

        verifyNoInteractions(crudWeather);
    }
}