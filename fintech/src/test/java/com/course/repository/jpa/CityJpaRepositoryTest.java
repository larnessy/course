package com.course.repository.jpa;

import com.course.model.entity.City;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@Transactional
class CityJpaRepositoryTest {

    @SpyBean
    private CityJpaRepository cityJpaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

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

        cityJpaRepository.save(city);
        entityManager.flush();
        int actuallyCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);

        verify(cityJpaRepository, times(1)).save(city);
        verify(cityJpaRepository, times(1)).save(any(City.class));
        assertEquals(oldCount + 1, actuallyCount);
        assertNotEquals(city.getId(), 0);
    }

    @Test
    void save_failedSaveDueToUniquenessViolation_thrownConstraintViolationExceptionAndCountDidNotChanged() {
        int oldCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);
        City city = new City(1, "London");

        assertThrows(ConstraintViolationException.class, () -> {
            cityJpaRepository.save(city);
            entityManager.flush();
        });
        int actuallyCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);

        verify(cityJpaRepository, times(1)).save(city);
        verify(cityJpaRepository, times(1)).save(any(City.class));
        assertEquals(oldCount, actuallyCount);
    }

    @Test
    void getById_successfulGetById_gotCityById() {
        City oldCity = new City(1, "New York");

        City newCity = cityJpaRepository.getById(oldCity.getId());

        verify(cityJpaRepository, times(1)).getById(oldCity.getId());
        verify(cityJpaRepository, times(1)).getById(anyInt());
        assertEquals(oldCity, newCity);
    }

    @Test
    void getById_thereIsNoCityWithThisId_thrownConstraintViolationException() {
        City city;

        city = cityJpaRepository.getById(Integer.MAX_VALUE);
        assertThrows(EntityNotFoundException.class, () -> city.getName());

        verify(cityJpaRepository, times(1)).getById(Integer.MAX_VALUE);
        verify(cityJpaRepository, times(1)).getById(anyInt());
    }

    @Test
    void update_successfulUpdate_updateRowWithThisId() {
        City city = new City(1, "Chelyabinsk");

        cityJpaRepository.save(city);
        entityManager.flush();
        String name = jdbcTemplate.queryForObject("SELECT name FROM city WHERE id = 1", String.class);

        verify(cityJpaRepository, times(1)).save(city);
        verify(cityJpaRepository, times(1)).save(any(City.class));
        assertEquals(city.getName(), name);
    }

    @Test
    void update_failedUpdateDueToUniquenessViolation_thrownConstraintViolationExceptionAndCountDidNotChanged() {
        int oldCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);
        City city = new City(1, "London");

        assertThrows(ConstraintViolationException.class, () -> {
            cityJpaRepository.save(city);
            entityManager.flush();
        });
        int actuallyCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);

        verify(cityJpaRepository, times(1)).save(city);
        verify(cityJpaRepository, times(1)).save(any(City.class));
        assertEquals(oldCount, actuallyCount);
    }

    @Test
    void deleteById_successfulDeleteById_countCitiesInDbLessByOneAndThereIsNoCityWithThisId() {
        int id = 1;
        int oldCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);

        cityJpaRepository.deleteById(id);
        entityManager.flush();
        int actuallyCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);
        int countOfElementWithThisId = jdbcTemplate
                .queryForObject("SELECT COUNT(*) FROM city WHERE id = 1", Integer.class);

        verify(cityJpaRepository, times(1)).deleteById(id);
        verify(cityJpaRepository, times(1)).deleteById(anyInt());
        assertEquals(oldCount - 1, actuallyCount);
        assertEquals(countOfElementWithThisId, 0);
    }

    @Test
    void deleteById_thereIsNoCityWithThisId_countCitiesInDbDidNotChange() {
        int id = 10;
        int oldCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);

        cityJpaRepository.deleteById(id);
        entityManager.flush();
        int actuallyCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);
        int countOfElementWithThisId = jdbcTemplate
                .queryForObject("SELECT COUNT(*) FROM city WHERE id = 10", Integer.class);

        verify(cityJpaRepository, times(1)).deleteById(id);
        verify(cityJpaRepository, times(1)).deleteById(anyInt());
        assertEquals(oldCount, actuallyCount);
        assertEquals(countOfElementWithThisId, 0);
    }
}