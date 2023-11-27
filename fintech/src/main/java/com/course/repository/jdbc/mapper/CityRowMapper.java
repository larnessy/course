package com.course.repository.jdbc.mapper;

import com.course.model.entity.City;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityRowMapper implements RowMapper<City> {
    @Override
    public City mapRow(ResultSet rs, int rowNum) throws SQLException {
        final City city = new City();
        city.setId(rs.getInt("id"));
        city.setName(rs.getString("name"));
        return city;
    }
}
