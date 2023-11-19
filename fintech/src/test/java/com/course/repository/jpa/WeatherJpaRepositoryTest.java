package com.course.repository.jpa;

import com.course.model.entity.City;
import com.course.model.entity.WeatherEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@Transactional
class WeatherJpaRepositoryTest {

    @SpyBean
    WeatherJpaRepository weatherJpaRepository;

    @Container
    static GenericContainer h2 = new GenericContainer(DockerImageName.parse("oscarfonts/h2"))
            .withExposedPorts(1521, 81)
            .withEnv("H2_OPTIONS", "-ifNotExists")
            .waitingFor(Wait.defaultWaitStrategy());

    @DynamicPropertySource
    static void setDynamicProperty(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url",
                () -> "jdbc:h2:tcp://localhost:" + h2.getMappedPort(1521) + "/test");
    }

    @Test
    void findTopByCityNameOrderByDateTimeDesc_successful_gotWeatherWithThisCity() {
        City city = new City(2, "Los Angeles");

        WeatherEntity weatherEntity = weatherJpaRepository
                .findTopByCityNameOrderByDateTimeDesc(city.getName())
                .orElse(null);

        assertNotNull(weatherEntity);
        verify(weatherJpaRepository, times(1)).findTopByCityNameOrderByDateTimeDesc(city.getName());
        verify(weatherJpaRepository, times(1)).findTopByCityNameOrderByDateTimeDesc(anyString());
        assertEquals(city, weatherEntity.getCity());
    }
}