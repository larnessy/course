package com.course.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "city", cascade = CascadeType.REMOVE)
    private List<WeatherEntity> weatherEntities = new ArrayList<>();

    public City(String name) {
        this.name = name;
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
