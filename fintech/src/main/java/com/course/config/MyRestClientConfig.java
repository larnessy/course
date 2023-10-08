package com.course.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class MyRestClientConfig {
    @Value("${base.api.url}")
    private String baseURL;

    @Bean
    @Qualifier("weatherApiClient")
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(baseURL)
                .build();
    }
}
