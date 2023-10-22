package com.course.service.crud.db.jpa;

import com.course.exception.myException.db.UnknownProblemWithDb;
import com.course.model.entity.WeatherCondition;
import com.course.repository.jpa.WeatherConditionJpaRepository;
import com.course.service.crud.db.contract.WeatherConditionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class JpaWeatherConditionService implements WeatherConditionService {

    private WeatherConditionJpaRepository weatherConditionJpaRepository;

    @Autowired
    public JpaWeatherConditionService(WeatherConditionJpaRepository weatherConditionJpaRepository) {
        this.weatherConditionJpaRepository = weatherConditionJpaRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void save(WeatherCondition weatherCondition) {
        try {
            if (weatherCondition.getId() != 0) {
                throw new IllegalArgumentException("The id must not be set for a new weatherCondition");
            }

            weatherConditionJpaRepository.save(weatherCondition);

        } catch (DataAccessException ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new IllegalArgumentException("A weatherCondition with the same "
                    + "name have been already in the database");
        }
    }

    @Override
    public WeatherCondition getById(int id) {
        return weatherConditionJpaRepository.findById(id).orElse(null);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void update(WeatherCondition weatherCondition) {
        try {
            weatherConditionJpaRepository.save(weatherCondition);

        } catch (DataAccessException ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new UnknownProblemWithDb("Failed to update weatherCondition in database");
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void deleteById(int id) {
        try {
            weatherConditionJpaRepository.deleteById(id);
            ;

        } catch (DataIntegrityViolationException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (DataAccessException ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new UnknownProblemWithDb("Failed to delete weatherCondition from database");
        }
    }
}
