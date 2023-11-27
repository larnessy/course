package com.course.config;

import com.course.model.additional.DoubleLinkedList;
import com.course.repository.WeatherCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    private final CacheProperties cacheProperties;

    @Autowired
    public CacheConfig(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean
    public WeatherCacheRepository cacheRepository() {
        return new WeatherCacheRepository(cacheProperties.getSize(),
                cacheProperties.getMaxIdleTimeInMinutes(), new DoubleLinkedList<>());
    }
}
