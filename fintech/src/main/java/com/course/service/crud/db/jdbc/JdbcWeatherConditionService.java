package com.course.service.crud.db.jdbc;

import com.course.exception.myException.db.UnknownProblemWithDb;
import com.course.model.entity.WeatherCondition;
import com.course.repository.jdbc.WeatherConditionJdbcRepository;
import com.course.repository.jdbc.WeatherJdbcRepository;
import com.course.service.crud.db.contract.WeatherConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;

@Service
public class JdbcWeatherConditionService implements WeatherConditionService {
    private final TransactionTemplate transactionTemplate;
    private final WeatherConditionJdbcRepository weatherConditionJdbcRepository;
    private final WeatherJdbcRepository weatherJdbcRepository;

    @Autowired
    public JdbcWeatherConditionService(WeatherConditionJdbcRepository weatherConditionJdbcRepository,
                                       TransactionTemplate transactionTemplate,
                                       WeatherJdbcRepository weatherJdbcRepository) {
        this.weatherConditionJdbcRepository = weatherConditionJdbcRepository;
        this.transactionTemplate = transactionTemplate;
        this.weatherJdbcRepository = weatherJdbcRepository;
    }

    @Override
    public void insert(WeatherCondition weatherCondition) {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.execute(status -> {
            try {
                if (weatherCondition.getId() != null) {
                    throw new IllegalArgumentException("The id must not be set for a new weatherCondition");
                }

                weatherConditionJdbcRepository.insert(weatherCondition);

            } catch (DataAccessException ex) {
                status.setRollbackOnly();
                throw new IllegalArgumentException("A weatherCondition with the same "
                        + "name have been already in the database");
            }
            return null;
        });
    }

    @Override
    public Optional<WeatherCondition> getById(Integer id) {
        return weatherConditionJdbcRepository.getById(id);
    }

    @Override
    public void update(WeatherCondition weatherCondition) {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.execute(status -> {
            try {
                weatherConditionJdbcRepository.update(weatherCondition);
            } catch (DataAccessException ex) {
                status.setRollbackOnly();
                throw new UnknownProblemWithDb("Failed to update weatherCondition in database");
            }
            return null;
        });
    }

    @Override
    public void deleteById(Integer id) {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        transactionTemplate.execute(status -> {
            try {
                weatherJdbcRepository.deleteByWeatherConditionId(id);

                weatherConditionJdbcRepository.deleteById(id);

            } catch (DuplicateKeyException e) {
                status.setRollbackOnly();
            } catch (DataAccessException ex) {
                status.setRollbackOnly();
                throw new UnknownProblemWithDb("Failed to delete weatherCondition from database");
            }
            return null;
        });
    }
}
