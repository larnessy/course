package com.course.service.crud.db.jdbc;

import com.course.exception.myException.db.UnknownProblemWithDb;
import com.course.model.entity.City;
import com.course.model.entity.WeatherCondition;
import com.course.model.entity.WeatherEntity;
import com.course.repository.jdbc.CityJdbcRepository;
import com.course.repository.jdbc.WeatherConditionJdbcRepository;
import com.course.repository.jdbc.WeatherJdbcRepository;
import com.course.service.crud.db.contract.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class JdbcWeatherService implements WeatherService {

    private final TransactionTemplate transactionTemplate;
    private final WeatherJdbcRepository weatherJdbcRepository;
    private final CityJdbcRepository cityJdbcRepository;
    private final WeatherConditionJdbcRepository weatherConditionJdbcRepository;

    @Autowired
    public JdbcWeatherService(TransactionTemplate transactionTemplate,
                              WeatherJdbcRepository weatherJdbcRepository,
                              CityJdbcRepository cityJdbcRepository,
                              WeatherConditionJdbcRepository weatherConditionJdbcRepository) {
        this.transactionTemplate = transactionTemplate;
        this.weatherJdbcRepository = weatherJdbcRepository;
        this.cityJdbcRepository = cityJdbcRepository;
        this.weatherConditionJdbcRepository = weatherConditionJdbcRepository;
    }

    @Override
    public void save(WeatherEntity weatherEntity) {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        transactionTemplate.execute(status -> {
            try {
                if (weatherEntity.getId() != 0) {
                    throw new IllegalArgumentException("The id must not be set for a new weather");
                }

                City city = weatherEntity.getCity();
                WeatherCondition weatherCondition = weatherEntity.getWeatherCondition();

                if (city == null || weatherCondition == null) {
                    throw new IllegalArgumentException("City and weatherCondition must not be null");
                }

                if (city.getId() == 0) {
                    City cityFromDb = cityJdbcRepository.findByName(city.getName());

                    if (cityFromDb == null) {
                        cityJdbcRepository.save(city);
                    } else {
                        weatherEntity.setCity(cityFromDb);
                    }
                }

                if (weatherCondition.getId() == 0) {
                    WeatherCondition weatherConditionFromDb = weatherConditionJdbcRepository.
                            findByName(weatherCondition.getName());

                    if (weatherConditionFromDb == null) {
                        weatherConditionJdbcRepository.save(weatherCondition);
                    } else {
                        weatherEntity.setWeatherCondition(weatherConditionFromDb);
                    }
                }

                weatherJdbcRepository.save(weatherEntity);

            } catch (DataAccessException ex) {
                status.setRollbackOnly();
                throw new IllegalArgumentException("A weather with the same name have been already in the database");
            }
            return null;
        });
    }

    @Override
    public WeatherEntity getById(int id) {
        return weatherJdbcRepository.getById(id);
    }

    @Override
    public void update(WeatherEntity weatherEntity) {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        transactionTemplate.execute(status -> {
            try {
                City city = weatherEntity.getCity();
                WeatherCondition weatherCondition = weatherEntity.getWeatherCondition();

                if (city == null || weatherCondition == null) {
                    throw new IllegalArgumentException("City and weatherCondition must not be null");
                }

                if (city.getId() == 0) {
                    City cityFromDb = cityJdbcRepository.findByName(city.getName());

                    if (cityFromDb == null) {
                        cityJdbcRepository.save(city);
                    } else {
                        weatherEntity.setCity(cityFromDb);
                    }
                }

                if (weatherCondition.getId() == 0) {
                    WeatherCondition weatherConditionFromDb = weatherConditionJdbcRepository.
                            findByName(weatherCondition.getName());

                    if (weatherConditionFromDb == null) {
                        weatherConditionJdbcRepository.save(weatherCondition);
                    } else {
                        weatherEntity.setWeatherCondition(weatherConditionFromDb);
                    }
                }

                weatherJdbcRepository.update(weatherEntity);

            }  catch (DataAccessException ex) {
                status.setRollbackOnly();
                throw new UnknownProblemWithDb("Failed to update weather in database");
            }
            return null;
        });
    }

    @Override
    public void deleteById(int id) {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.execute(status -> {
            try {
                weatherJdbcRepository.deleteById(id);

            } catch (DuplicateKeyException e) {
                status.setRollbackOnly();
            } catch (DataAccessException ex) {
                status.setRollbackOnly();
                throw new UnknownProblemWithDb("Failed to delete weather from database");
            }
            return null;
        });
    }
}