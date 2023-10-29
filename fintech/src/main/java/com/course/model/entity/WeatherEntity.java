package com.course.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "weather")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "city_id")
    @NotNull
    private City city;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "weather_condition_id")
    @NotNull
    private WeatherCondition weatherCondition;

    @NotNull
    private double temperature;

    @Column(name = "date_time")
    @NotNull
    private LocalDateTime dateTime;

    public WeatherEntity(City city, WeatherCondition weatherCondition, double temperature, LocalDateTime dateTime) {
        this.city = city;
        this.weatherCondition = weatherCondition;
        this.temperature = temperature;
        this.dateTime = dateTime;
    }
}
