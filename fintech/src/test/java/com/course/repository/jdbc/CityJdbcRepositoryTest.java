package com.course.repository.jdbc;

import com.course.model.entity.City;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@Transactional
class CityJdbcRepositoryTest {
    @SpyBean
    private CityJdbcRepository cityJdbcRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    void save_successfulSave_countCitiesInDbMoreByOneAndCityIdChanged() {
        int oldCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);
        City city = new City("Tomsk");

        cityJdbcRepository.save(city);
        int actuallyCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);

        verify(cityJdbcRepository, times(1)).save(city);
        verify(cityJdbcRepository, times(1)).save(any(City.class));
        assertEquals(oldCount + 1, actuallyCount);
        assertNotEquals(city.getId(), 0);
    }

    @Test
    void save_failedSaveDueToUniquenessViolation_thrownDataAccessExceptionAndCountNotChanged() {
        int oldCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);
        City city = new City(1, "London");

        assertThrows(DataAccessException.class, () -> cityJdbcRepository.save(city));
        int actuallyCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);

        verify(cityJdbcRepository, times(1)).save(city);
        verify(cityJdbcRepository, times(1)).save(any(City.class));
        assertEquals(oldCount, actuallyCount);
    }

    @Test
    void getById_successfulGetById_gotCityById() {
        City oldCity = new City(1, "New York");

        City newCity = cityJdbcRepository.getById(oldCity.getId()).orElse(null);

        verify(cityJdbcRepository, times(1)).getById(oldCity.getId());
        verify(cityJdbcRepository, times(1)).getById(anyInt());
        assertEquals(oldCity, newCity);
    }

    @Test
    void getById_thereIsNoCityWithThisId_returnedNull() {
        City city;

        city = cityJdbcRepository.getById(Integer.MAX_VALUE).orElse(null);

        verify(cityJdbcRepository, times(1)).getById(Integer.MAX_VALUE);
        verify(cityJdbcRepository, times(1)).getById(anyInt());
        assertNull(city);
    }

    @Test
    void update_successfulUpdate_updateRowWithThisId() {
        City city = new City(1, "Chelyabinsk");

        cityJdbcRepository.update(city);
        String name = jdbcTemplate.queryForObject("SELECT name FROM city WHERE id = 1", String.class);

        verify(cityJdbcRepository, times(1)).update(city);
        verify(cityJdbcRepository, times(1)).update(any(City.class));
        assertEquals(city.getName(), name);
    }

    @Test
    void update_failedUpdateDueToUniquenessViolation_thrownDataAccessException() {
        City city = new City(1, "London");

        assertThrows(DataAccessException.class, () -> cityJdbcRepository.update(city));

        verify(cityJdbcRepository, times(1)).update(city);
        verify(cityJdbcRepository, times(1)).update(any(City.class));
    }

    @Test
    void deleteById_successfulDeleteById_countCitiesInDbLessByOneAndThereIsNoCityWithThisId() {
        int id = 1;
        int oldCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);

        cityJdbcRepository.deleteById(id);
        int actuallyCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);
        int countOfElementWithThisId = jdbcTemplate
                .queryForObject("SELECT COUNT(*) FROM city WHERE id = 1", Integer.class);

        verify(cityJdbcRepository, times(1)).deleteById(id);
        verify(cityJdbcRepository, times(1)).deleteById(anyInt());
        assertEquals(oldCount - 1, actuallyCount);
        assertEquals(countOfElementWithThisId, 0);
    }

    @Test
    void deleteById_thereIsNoCityWithThisId_countCitiesInDbDidNotChange() {
        int id = 10;
        int oldCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);

        cityJdbcRepository.deleteById(id);
        int actuallyCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);
        int countOfElementWithThisId = jdbcTemplate
                .queryForObject("SELECT COUNT(*) FROM city WHERE id = 10", Integer.class);

        verify(cityJdbcRepository, times(1)).deleteById(id);
        verify(cityJdbcRepository, times(1)).deleteById(anyInt());
        assertEquals(oldCount, actuallyCount);
        assertEquals(countOfElementWithThisId, 0);
    }
}