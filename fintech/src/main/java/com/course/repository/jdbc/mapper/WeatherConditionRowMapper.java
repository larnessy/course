package com.course.repository.jdbc.mapper;

import com.course.model.entity.WeatherCondition;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WeatherConditionRowMapper implements RowMapper<WeatherCondition> {

    @Override
    public WeatherCondition mapRow(ResultSet rs, int rowNum) throws SQLException {
        final WeatherCondition weatherCondition = new WeatherCondition();
        weatherCondition.setId(rs.getInt("id"));
        weatherCondition.setName(rs.getString("name"));
        return weatherCondition;
    }
}
