package com.course.controller;

import com.course.model.Weather;
import com.course.service.crud.CRUDWeather;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// for test POST endpoint

@WebMvcTest(CRUDController.class)
class CRUDControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CRUDWeather crudWeather;

    @SneakyThrows
    @Test
    void addCity_successfulAddCity_statusIsOkAndMethodAddCityCalledOnceAndResponseContractWasNotChanged() {
        Weather weather = new Weather(100, "London", 10, LocalDateTime.now());
        doNothing().when(crudWeather).addCity(any());

        mockMvc.perform(post("/api/weather/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(weather))
                ).andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(crudWeather, times(1)).addCity(weather);

    }

    @SneakyThrows
    @Test
    void addCity_notRightDataType_statusIsNotOkAndMethodAddCityDidNotCallAndResponseContractWasNotChanged() {
        String empty = "";
        doNothing().when(crudWeather).addCity(any());

        mockMvc.perform(post("/api/weather/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empty)
                ).andExpect(status().is5xxServerError())
                .andExpect(content().string("{\"info\":\"Unknown server error\"}"));

        verifyNoInteractions(crudWeather);
    }
}