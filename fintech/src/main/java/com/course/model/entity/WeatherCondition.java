package com.course.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "weather_condition")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WeatherCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "weatherCondition", cascade = CascadeType.REMOVE)
    private List<WeatherEntity> weatherEntities = new ArrayList<>();

    public WeatherCondition(String name) {
        this.name = name;
    }

    public WeatherCondition(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
