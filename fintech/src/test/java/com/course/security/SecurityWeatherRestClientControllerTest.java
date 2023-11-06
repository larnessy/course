package com.course.security;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SecurityWeatherRestClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void testWithJDBCAuthenticationResourceAccess_failed_byWithoutAuthentication() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/test/jdbc/London"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @SneakyThrows
    public void testWithJPAAuthenticationResourceAccess_failed_byWithoutAuthentication() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/test/jpa/London"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "userok", password = "I'mUserI'motBear", roles = "USER")
    public void testGetWithJDBCInAuthenticationResourceAccess_successful_byUserAuthorization() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/test/jdbc/London"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "userok", password = "I'mUserI'motBear", roles = "USER")
    public void testGetWithJPAInAuthenticationResourceAccess_successful_byUserAuthorization() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/test/jpa/London"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "admincheg", password = "SprintIsMyLife№1", roles = "ADMIN")
    public void testGetWithJDBCInAuthenticationResourceAccess_successful_byAdminAuthorization() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/test/jdbc/London"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "admincheg", password = "SprintIsMyLife№1", roles = "ADMIN")
    public void testGetWithJPAInAuthenticationResourceAccess_successful_byAdminAuthorization() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/test/jpa/London"))
                .andExpect(status().isOk());
    }
}
