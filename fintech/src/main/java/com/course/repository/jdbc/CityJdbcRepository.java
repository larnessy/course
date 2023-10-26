package com.course.repository.jdbc;

import com.course.model.entity.City;
import com.course.repository.jdbc.mapper.CityRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CityJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CityJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(City city) {
        String sql = "INSERT INTO city (name) VALUES (:name)";
        Map<String, Object> params = new HashMap<>();
        params.put("name", city.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                sql,
                new MapSqlParameterSource(params),
                keyHolder,
                new String[] { "id" });

        city.setId(keyHolder.getKey() == null ? 0 : keyHolder.getKey().intValue());
    }

    public Optional<City> getById(int id) {
        String sql = "SELECT * FROM city WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        List<City> cities = namedParameterJdbcTemplate.query(sql, params, new CityRowMapper());
        return cities.isEmpty() ? Optional.empty() : Optional.ofNullable(cities.get(0));
    }

    public Optional<City> findByName(String name) {
        String sql = "SELECT * FROM city WHERE name = :name";
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        List<City> cities = namedParameterJdbcTemplate.query(sql, params, new CityRowMapper());
        return cities.isEmpty() ? Optional.empty() : Optional.ofNullable(cities.get(0));
    }

    public void update(City city) {
        String sql = "UPDATE city SET name = :name WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("name", city.getName());
        params.put("id", city.getId());
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM city WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }

}
