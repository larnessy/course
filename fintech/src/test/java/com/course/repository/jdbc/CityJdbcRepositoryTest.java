package com.course.repository.jdbc;

import com.course.model.entity.City;
import com.course.repository.CityRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

class CityJdbcRepositoryTest extends CityRepositoryTest {
    @SpyBean
    private CityJdbcRepository cityJdbcRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void save_successfulSave_countCitiesInDbMoreByOneAndCityIdChanged() {
        int oldCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);
        City city = new City("Tomsk");

        cityJdbcRepository.insert(city);
        int actuallyCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);

        assertEquals(oldCount + 1, actuallyCount);
        assertNotEquals(city.getId(), 0);
    }

    @Test
    void save_failedSaveDueToUniquenessViolation_thrownDataAccessExceptionAndCountNotChanged() {
        int oldCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);
        City city = new City(1, "London");

        assertThrows(DataAccessException.class, () -> cityJdbcRepository.insert(city));
        int actuallyCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);

        assertEquals(oldCount, actuallyCount);
    }

    @Test
    void getById_successfulGetById_gotCityById() {
        City oldCity = new City(1, "New York");

        City newCity = cityJdbcRepository.getById(oldCity.getId()).orElse(null);

        assertEquals(oldCity, newCity);
    }

    @Test
    void getById_thereIsNoCityWithThisId_returnedNull() {
        City city;

        city = cityJdbcRepository.getById(Integer.MAX_VALUE).orElse(null);

        assertNull(city);
    }

    @Test
    void update_successfulUpdate_updateRowWithThisId() {
        City city = new City(1, "Chelyabinsk");

        cityJdbcRepository.update(city);
        String name = jdbcTemplate.queryForObject("SELECT name FROM city WHERE id = 1", String.class);

        assertEquals(city.getName(), name);
    }

    @Test
    void update_failedUpdateDueToUniquenessViolation_thrownDataAccessException() {
        City city = new City(1, "London");

        assertThrows(DataAccessException.class, () -> cityJdbcRepository.update(city));
    }

    @Test
    void deleteById_successfulDeleteById_countCitiesInDbLessByOneAndThereIsNoCityWithThisId() {
        int id = 1;
        int oldCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);

        cityJdbcRepository.deleteById(id);
        int actuallyCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);
        int countOfElementWithThisId = jdbcTemplate
                .queryForObject("SELECT COUNT(*) FROM city WHERE id = 1", Integer.class);

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

        assertEquals(oldCount, actuallyCount);
        assertEquals(countOfElementWithThisId, 0);
    }
}