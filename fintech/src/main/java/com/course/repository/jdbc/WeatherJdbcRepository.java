package com.course.repository.jdbc;

import com.course.model.entity.WeatherEntity;
import com.course.repository.jdbc.mapper.WeatherEntityRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WeatherJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public WeatherJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(WeatherEntity weatherEntity) {
        String sql = "INSERT INTO weather (city_id, weather_condition_id, temperature, date_time) " +
                "VALUES (:cityId, :weatherConditionId, :temperature, :dateTime)";
        Map<String, Object> params = new HashMap<>();
        params.put("cityId", weatherEntity.getCity().getId());
        params.put("weatherConditionId", weatherEntity.getWeatherCondition().getId());
        params.put("temperature", weatherEntity.getTemperature());
        params.put("dateTime", weatherEntity.getDateTime());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                sql,
                new MapSqlParameterSource(params),
                keyHolder,
                new String[] { "id" });

        weatherEntity.setId(keyHolder.getKey() == null ? 0 : keyHolder.getKey().intValue());
    }

    public WeatherEntity getById(int id) {
        String sql = "SELECT w.id, w.temperature, w.date_time, c.id AS city_id, c.name AS city_name, " +
                "wc.id AS weather_condition_id, wc.name AS weather_condition_name " +
                "FROM weather w " +
                "INNER JOIN city c ON w.city_id = c.id " +
                "INNER JOIN weather_condition wc ON w.weather_condition_id = wc.id " +
                "WHERE w.id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        List<WeatherEntity> weatherEntities = namedParameterJdbcTemplate.query(sql, params, new WeatherEntityRowMapper());
        return weatherEntities.isEmpty() ? null : weatherEntities.get(0);
    }

    public void update(WeatherEntity weatherEntity) {
        String sql = "UPDATE weather " +
                "SET city_id = :cityId, weather_condition_id = :weatherConditionId, " +
                "temperature = :temperature, date_time = :dateTime " +
                "WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", weatherEntity.getId());
        params.put("cityId", weatherEntity.getCity().getId());
        params.put("weatherConditionId", weatherEntity.getWeatherCondition().getId());
        params.put("temperature", weatherEntity.getTemperature());
        params.put("dateTime", weatherEntity.getDateTime());
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM weather WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
