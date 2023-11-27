package com.course.security;

import com.course.model.Weather;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class SecurityInCRUDControllerTest extends SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void testAuthenticationResourceAccess_failed_byWithoutAuthentication() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "userok", password = "I'mUserI'motBear", roles = "USER")
    public void testGetInAuthenticationResourceAccess_successful_byUserAuthorization() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/1"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "admincheg", password = "SprintIsMyLife№1", roles = "ADMIN")
    public void testGetInAuthenticationResourceAccess_successful_byAdminAuthorization() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/1"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "userok", password = "I'mUserI'motBear", roles = "USER")
    public void testDeleteInAdminResourceAccess_failed_byUserAuthorization() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/weather/1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "admincheg", password = "SprintIsMyLife№1", roles = "ADMIN")
    public void testDeleteInAdminResourceAccess_successful_byAdminAuthorization() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/weather/1"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "userok", password = "I'mUserI'motBear", roles = "USER")
    public void testPostInAuthenticationResourceAccess_failed_byUserAuthorization() {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/weather/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createWeather())))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "admincheg", password = "SprintIsMyLife№1", roles = "ADMIN")
    public void testPostInAuthenticationResourceAccess_successful_byAdminAuthorization() {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/weather/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createWeather())))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "userok", password = "I'mUserI'motBear", roles = "USER")
    public void testPutInAuthenticationResourceAccess_failed_byUserAuthorization() {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/weather/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createWeather())))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "admincheg", password = "SprintIsMyLife№1", roles = "ADMIN")
    public void testPutInAuthenticationResourceAccess_successful_byAdminAuthorization() {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/weather/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createWeather())))
                .andExpect(status().isOk());
    }

    private Weather createWeather() {
        return new Weather(10, "TestRegion", 25, LocalDateTime.now());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
