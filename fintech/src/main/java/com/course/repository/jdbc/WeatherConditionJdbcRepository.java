package com.course.repository.jdbc;

import com.course.model.entity.WeatherCondition;
import com.course.repository.jdbc.mapper.WeatherConditionRowMapper;
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
public class WeatherConditionJdbcRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public WeatherConditionJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(WeatherCondition weatherCondition) {
        String sql = "INSERT INTO weather_condition (name) VALUES (:name)";
        Map<String, Object> params = new HashMap<>();
        params.put("name", weatherCondition.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                sql,
                new MapSqlParameterSource(params),
                keyHolder,
                new String[] { "id" });

        weatherCondition.setId(keyHolder.getKey() == null ? 0 : keyHolder.getKey().intValue());
    }

    public Optional<WeatherCondition> getById(int id) {
        String sql = "SELECT * FROM weather_condition WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        List<WeatherCondition> weatherConditions =
                namedParameterJdbcTemplate.query(sql, params, new WeatherConditionRowMapper());
        return weatherConditions.isEmpty() ? Optional.empty() : Optional.ofNullable(weatherConditions.get(0));
    }

    public Optional<WeatherCondition> findByName(String name) {
        String sql = "SELECT * FROM weather_condition WHERE name = :name";
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        List<WeatherCondition> weatherConditions =
                namedParameterJdbcTemplate.query(sql, params, new WeatherConditionRowMapper());
        return weatherConditions.isEmpty() ? Optional.empty() : Optional.ofNullable(weatherConditions.get(0));
    }

    public void update(WeatherCondition weatherCondition) {
        String sql = "UPDATE weather_condition SET name = :name WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("name", weatherCondition.getName());
        params.put("id", weatherCondition.getId());
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM weather_condition WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }

}
