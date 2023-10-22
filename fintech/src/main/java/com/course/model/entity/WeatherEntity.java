package com.course.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "weather")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "weather_condition_id")
    private WeatherCondition weatherCondition;

    private double temperature;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    public WeatherEntity(City city, WeatherCondition weatherCondition, double temperature, LocalDateTime dateTime) {
        this.city = city;
        this.weatherCondition = weatherCondition;
        this.temperature = temperature;
        this.dateTime = dateTime;
    }
}
