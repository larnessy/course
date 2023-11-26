package com.course.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// to create bins to avoid loops
@Configuration
public class AdditionalConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Qualifier("cityNames")
    public String[] cityNames () {
        return new String[]{"New York", "Los Angeles", "Chicago", "Moscow", "London"};
    }
}
