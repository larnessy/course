package com.course.model.entity.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @NotNull
    @Size(min = 5, max = 200, message = "Имя должно быть длиннее 5 и короче 200 символов")
    @Setter
    @Column(name = "name")
    private String username;

    @NotNull
    @Size(min = 5, max = 200, message = "Пароль должен быть длиннее 5 и короче 200 символов")
    @Setter
    private String password;

    @Transient
    @Getter
    @Setter
    private String passwordConfirm;

    @Transient
    private boolean accountNonExpired = true;
    @Transient
    private boolean accountNonLocked = true;
    @Transient
    private boolean credentialsNonExpired = true;
    @Transient
    private boolean enabled = true;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Getter
    @Setter
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
