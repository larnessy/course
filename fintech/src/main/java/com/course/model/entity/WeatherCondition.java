package com.course.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "weather_condition")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WeatherCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public WeatherCondition(String name) {
        this.name = name;
    }
}
