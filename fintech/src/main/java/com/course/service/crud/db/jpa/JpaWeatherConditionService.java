package com.course.service.crud.db.jpa;

import com.course.exception.myException.db.UnknownProblemWithDb;
import com.course.model.entity.WeatherCondition;
import com.course.repository.jpa.WeatherConditionJpaRepository;
import com.course.service.crud.db.contract.WeatherConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class JpaWeatherConditionService implements WeatherConditionService {

    private final WeatherConditionJpaRepository weatherConditionJpaRepository;

    @Autowired
    public JpaWeatherConditionService(WeatherConditionJpaRepository weatherConditionJpaRepository) {
        this.weatherConditionJpaRepository = weatherConditionJpaRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void insert(WeatherCondition weatherCondition) {
        try {
            if (weatherCondition.getId() != null) {
                throw new IllegalArgumentException("The id must not be set for a new weatherCondition");
            }

            weatherConditionJpaRepository.save(weatherCondition);

        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("A weatherCondition with the same "
                    + "name have been already in the database");
        } catch (DataAccessException ex) {
            throw new UnknownProblemWithDb("Failed to insert weatherCondition in database");
        }
    }

    @Override
    public Optional<WeatherCondition> getById(Integer id) {
        return weatherConditionJpaRepository.findById(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = DataAccessException.class)
    @Override
    public void update(WeatherCondition weatherCondition) {
        try {
            weatherConditionJpaRepository.save(weatherCondition);

        } catch (DataAccessException ex) {
            throw new UnknownProblemWithDb("Failed to update weatherCondition in database");
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void deleteById(Integer id) {
        try {
            weatherConditionJpaRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            // ignore
        } catch (DataAccessException ex) {
            throw new UnknownProblemWithDb("Failed to delete weatherCondition from database");
        }
    }
}
