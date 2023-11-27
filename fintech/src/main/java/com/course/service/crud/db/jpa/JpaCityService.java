package com.course.service.crud.db.jpa;

import com.course.exception.myException.db.UnknownProblemWithDb;
import com.course.model.entity.City;
import com.course.repository.jpa.CityJpaRepository;
import com.course.service.crud.db.contract.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Optional;

@Service
public class JpaCityService implements CityService {

    private CityJpaRepository cityJpaRepository;

    @Autowired
    public JpaCityService(CityJpaRepository cityJpaRepository) {
        this.cityJpaRepository = cityJpaRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void insert(City city) {
        try {
            if (city.getId() != null) {
                throw new IllegalArgumentException("The id must not be set for a new city");
            }

            cityJpaRepository.save(city);

        } catch (DataAccessException ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new IllegalArgumentException("A city with the same name have been already in the database");
        }
    }

    @Override
    public Optional<City> getById(Integer id) {
        return cityJpaRepository.findById(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void update(City city) {
        try {
            cityJpaRepository.save(city);

        } catch (DataAccessException ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new UnknownProblemWithDb("Failed to update city in database");
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void deleteById(Integer id) {
        try {
            cityJpaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (DataAccessException ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new UnknownProblemWithDb("Failed to delete city from database");
        }
    }
}
