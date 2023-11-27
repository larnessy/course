package com.course.security;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
public class AuthenticationTest extends SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void testUserRegistrationAndAuthentication_successful_anonymousResourcesAreNotAccessed() {
        mockMvc.perform(MockMvcRequestBuilders.post("/createUser")
                        .param("username", "ValidUsername")
                        .param("password", "ValidPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register"))
                .andExpect(MockMvcResultMatchers.flash().attribute("msg", "Register Successfully"));

        MvcResult loginResult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "ValidUsername")
                        .param("password", "ValidPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/")).andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/register")
                        .session((MockHttpSession) loginResult.getRequest().getSession()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @SneakyThrows
    public void testUserRegistrationAndAuthentication_failed_loginErrorByInvalidPassword() {
        mockMvc.perform(MockMvcRequestBuilders.post("/createUser")
                        .param("username", "ValidUsername")
                        .param("password", "ValidPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register"))
                .andExpect(MockMvcResultMatchers.flash().attribute("msg", "Register Successfully"));

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "ValidUsername")
                        .param("password", "ValidPasssdfsdword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error"));
    }

    @Test
    @SneakyThrows
    public void testAdminAuthentication_successful_anonymousResourcesAreNotAccessed() {
        MvcResult loginResult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "admincheg")
                        .param("password", "SprintIsMyLife№1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/")).andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/register")
                        .session((MockHttpSession) loginResult.getRequest().getSession()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @SneakyThrows
    public void testAdminAuthentication_failed_loginErrorByInvalidLogin() {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "admisn")
                        .param("password", "SprintIsMyLife№1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error"));
    }
}
