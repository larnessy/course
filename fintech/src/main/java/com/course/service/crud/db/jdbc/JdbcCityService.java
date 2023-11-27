package com.course.service.crud.db.jdbc;

import com.course.exception.myException.db.UnknownProblemWithDb;
import com.course.model.entity.City;
import com.course.repository.jdbc.CityJdbcRepository;
import com.course.repository.jdbc.WeatherJdbcRepository;
import com.course.service.crud.db.contract.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;


@Service
public class JdbcCityService implements CityService {

    private final TransactionTemplate transactionTemplate;
    private final CityJdbcRepository cityJdbcRepository;
    private final WeatherJdbcRepository weatherJdbcRepository;

    @Autowired
    public JdbcCityService(TransactionTemplate transactionTemplate,
                           CityJdbcRepository cityJdbcRepository,
                           WeatherJdbcRepository weatherJdbcRepository) {
        this.transactionTemplate = transactionTemplate;
        this.cityJdbcRepository = cityJdbcRepository;
        this.weatherJdbcRepository = weatherJdbcRepository;
    }

    @Override
    public void insert(City city) {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.execute(status -> {
            try {
                if (city.getId() != null) {
                    throw new IllegalArgumentException("The id must not be set for a new city");
                }

                cityJdbcRepository.insert(city);

            } catch (DuplicateKeyException e) {
                throw new IllegalArgumentException("A city with the same name have been already in the database");
            } catch (DataAccessException ex) {
                throw new UnknownProblemWithDb("Failed to insert city in database");
            }
            return null;
        });
    }

    @Override
    public Optional<City> getById(Integer id) {
        return cityJdbcRepository.getById(id);
    }

    @Override
    public void update(City city) {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.execute(status -> {
            try {
                cityJdbcRepository.update(city);

            } catch (DataAccessException ex) {
                status.setRollbackOnly();
                throw new UnknownProblemWithDb("Failed to update city in database");
            }
            return null;
        });
    }

    @Override
    public void deleteById(Integer id) {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        transactionTemplate.execute(status -> {
            try {
                weatherJdbcRepository.deleteByCityId(id);

                cityJdbcRepository.deleteById(id);

            } catch (DuplicateKeyException e) {
                // ignore
            } catch (DataAccessException ex) {
                throw new UnknownProblemWithDb("Failed to delete city from database");
            }
            return null;
        });
    }

}
