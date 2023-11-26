package com.course.config;

import com.course.model.additional.DoubleLinkedList;
import com.course.repository.CacheRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Value("${cache.course.size}")
    private int maxSize;

    @Value("${cache.course.maxIdleTimeInMinutes}")
    private int maxIdleTimeInMinutes;

    @Bean
    public CacheRepository cacheRepository() {
        return new CacheRepository(maxSize, maxIdleTimeInMinutes, new DoubleLinkedList<>());
    }
}
