package com.course.repository.jdbc.mapper;

import com.course.model.entity.City;
import com.course.model.entity.WeatherCondition;
import com.course.model.entity.WeatherEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WeatherEntityRowMapper implements RowMapper<WeatherEntity> {

    @Override
    public WeatherEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setId(rs.getInt("id"));
        weatherEntity.setTemperature(rs.getDouble("temperature"));
        weatherEntity.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());

        City city = new City();
        city.setId(rs.getInt("city_id"));
        city.setName(rs.getString("city_name"));
        weatherEntity.setCity(city);

        WeatherCondition weatherCondition = new WeatherCondition();
        weatherCondition.setId(rs.getInt("weather_condition_id"));
        weatherCondition.setName(rs.getString("weather_condition_name"));
        weatherEntity.setWeatherCondition(weatherCondition);

        return weatherEntity;
    }
}
