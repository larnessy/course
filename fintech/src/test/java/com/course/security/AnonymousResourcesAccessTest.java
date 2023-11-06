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
public class AnonymousResourcesAccessTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void testAnonymousResourceAccess_successful_byWithoutAuthentication() {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admincheg", password = "SprintIsMyLife№1", roles = "ADMIN")
    public void testAnonymousResourceAccess_failed_byAdminAuthorization() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "userok", password = "I'mUserI'motBear", roles = "USER")
    public void testAnonymousResourceAccess_failed_byUserAuthorization() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(status().is4xxClientError());
    }
}
