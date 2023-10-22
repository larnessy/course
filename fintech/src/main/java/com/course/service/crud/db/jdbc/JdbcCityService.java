package com.course.service.crud.db.jdbc;

import com.course.exception.myException.db.UnknownProblemWithDb;
import com.course.model.entity.City;
import com.course.repository.jdbc.CityJdbcRepository;
import com.course.repository.jdbc.WeatherJdbcRepository;
import com.course.service.crud.db.contract.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.dao.DuplicateKeyException;


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
    public void save(City city) {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.execute(status -> {
            try {
                if (city.getId() != 0) {
                    throw new IllegalArgumentException("The id must not be set for a new city");
                }

                cityJdbcRepository.save(city);

            } catch (DataAccessException ex) {
                status.setRollbackOnly();
                throw new IllegalArgumentException("A city with the same name have been already in the database");
            }
            return null;
        });
    }

    @Override
    public City getById(int id) {
        return cityJdbcRepository.getById(id);
    }

    @Override
    public void update(City city) {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.execute(status -> {
            try {
                cityJdbcRepository.update(city);

            }  catch (DataAccessException ex) {
                status.setRollbackOnly();
                throw new UnknownProblemWithDb("Failed to update city in database");
            }
            return null;
        });
    }

    @Override
    public void deleteById(int id) {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        transactionTemplate.execute(status -> {
            try {
                weatherJdbcRepository.deleteByCityId(id);

                cityJdbcRepository.deleteById(id);

            } catch (DuplicateKeyException e) {
                status.setRollbackOnly();
            } catch (DataAccessException ex) {
                status.setRollbackOnly();
                throw new UnknownProblemWithDb("Failed to delete city from database");
            }
            return null;
        });
    }

}
