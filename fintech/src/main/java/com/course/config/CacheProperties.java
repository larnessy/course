package com.course.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cache.course")
@Getter
@Setter
public class CacheProperties {
    private int size;
    private int maxIdleTimeInMinutes;
}
