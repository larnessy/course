package com.course.repository.jpa;

import com.course.model.entity.City;
import com.course.repository.CityRepositoryTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

class CityJpaRepositoryTest extends CityRepositoryTest {

    @SpyBean
    private CityJpaRepository cityJpaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Test
    void save_successfulSave_countCitiesInDbMoreByOneAndCityIdChanged() {
        int oldCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);
        City city = new City("Tomsk");

        cityJpaRepository.save(city);
        entityManager.flush();
        int actuallyCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM city", Integer.class);

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

        assertEquals(oldCount, actuallyCount);
    }

    @Test
    void getById_successfulGetById_gotCityById() {
        City oldCity = new City(1, "New York");

        City newCity = cityJpaRepository.getById(oldCity.getId());

        assertEquals(oldCity, newCity);
    }

    @Test
    void getById_thereIsNoCityWithThisId_thrownConstraintViolationException() {
        City city;

        city = cityJpaRepository.getById(Integer.MAX_VALUE);

        assertThrows(EntityNotFoundException.class, () -> city.getName());
    }

    @Test
    void update_successfulUpdate_updateRowWithThisId() {
        City city = new City(1, "Chelyabinsk");

        cityJpaRepository.save(city);
        entityManager.flush();
        String name = jdbcTemplate.queryForObject("SELECT name FROM city WHERE id = 1", String.class);

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

        assertEquals(oldCount, actuallyCount);
        assertEquals(countOfElementWithThisId, 0);
    }
}