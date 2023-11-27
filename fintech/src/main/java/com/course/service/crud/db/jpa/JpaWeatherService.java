package com.course.service.crud.db.jpa;

import com.course.exception.myException.db.UnknownProblemWithDb;
import com.course.model.entity.City;
import com.course.model.entity.WeatherCondition;
import com.course.model.entity.WeatherEntity;
import com.course.repository.jpa.CityJpaRepository;
import com.course.repository.jpa.WeatherConditionJpaRepository;
import com.course.repository.jpa.WeatherJpaRepository;
import com.course.service.crud.db.contract.WeatherService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class JpaWeatherService implements WeatherService {

    @PersistenceContext
    private EntityManager entityManager;

    private final WeatherJpaRepository weatherJpaRepository;
    private final CityJpaRepository cityJpaRepository;
    private final WeatherConditionJpaRepository weatherConditionJpaRepository;

    @Autowired
    public JpaWeatherService(WeatherJpaRepository weatherJpaRepository,
                             CityJpaRepository cityJpaRepository,
                             WeatherConditionJpaRepository weatherConditionJpaRepository) {
        this.weatherJpaRepository = weatherJpaRepository;
        this.cityJpaRepository = cityJpaRepository;
        this.weatherConditionJpaRepository = weatherConditionJpaRepository;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = DataAccessException.class)
    public void insert(WeatherEntity weatherEntity) {
        try {
            if (weatherEntity.getId() != null) {
                throw new IllegalArgumentException("The id must not be set for a new weather");
            }

            City city = weatherEntity.getCity();
            WeatherCondition weatherCondition = weatherEntity.getWeatherCondition();

            if (city.getId() == null) {
                City cityFromDb = cityJpaRepository.findByName(city.getName());
                city = cityFromDb == null ? city : cityFromDb;
                weatherEntity.setCity(city);
            }

            if (weatherCondition.getId() == null) {
                WeatherCondition weatherConditionFromDb = weatherConditionJpaRepository.findByName(weatherCondition.getName());
                weatherCondition = weatherConditionFromDb == null ? weatherCondition : weatherConditionFromDb;
                weatherEntity.setWeatherCondition(weatherCondition);
            }

            if (!entityManager.contains(city)) {
                weatherEntity.setCity(entityManager.merge(city));
            }

            if (!entityManager.contains(weatherCondition)) {
                weatherEntity.setWeatherCondition(entityManager.merge(weatherCondition));
            }

            weatherJpaRepository.save(weatherEntity);

        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("A weather with the same name have been already in the database");
        }  catch (DataAccessException ex) {
            throw new UnknownProblemWithDb("Failed to insert weather to database");
        }
    }

    @Override
    public Optional<WeatherEntity> getById(Integer id) {
        return weatherJpaRepository.findById(id);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = DataAccessException.class)
    public void update(WeatherEntity weatherEntity) {
        try {
            City city = weatherEntity.getCity();
            WeatherCondition weatherCondition = weatherEntity.getWeatherCondition();

            if (city.getId() == null) {
                City cityFromDb = cityJpaRepository.findByName(city.getName());
                city = cityFromDb == null ? city : cityFromDb;
                weatherEntity.setCity(city);
            }

            if (weatherCondition.getId() == null) {
                WeatherCondition weatherConditionFromDb = weatherConditionJpaRepository.findByName(weatherCondition.getName());
                weatherCondition = weatherConditionFromDb == null ? weatherCondition : weatherConditionFromDb;
                weatherEntity.setWeatherCondition(weatherCondition);
            }

            if (!entityManager.contains(city)) {
                weatherEntity.setCity(entityManager.merge(city));
            }

            if (!entityManager.contains(weatherCondition)) {
                weatherEntity.setWeatherCondition(entityManager.merge(weatherCondition));
            }

            weatherJpaRepository.save(weatherEntity);

        } catch (DataAccessException ex) {
            throw new UnknownProblemWithDb("Failed to update weather in database");
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = DataAccessException.class)
    @Override
    public void deleteById(Integer id) {
        try {
            weatherJpaRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            // ignore
        } catch (DataAccessException ex) {
            throw new UnknownProblemWithDb("Failed to delete weather from database");
        }
    }
}
