package com.course.model.entity.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    public Role(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
