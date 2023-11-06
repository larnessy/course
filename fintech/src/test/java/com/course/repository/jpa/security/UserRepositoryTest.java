package com.course.repository.jpa.security;

import com.course.model.entity.security.Role;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.course.model.entity.security.User;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Test
    void findUserByUsername() {
        User user = userRepository.findById(1L).orElse(null);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getUsername());
        assertNotNull(user.getPassword());
        assertNotEquals(user.getRoles().isEmpty(), true);
    }

    @Test
    void saveUser() {
        User user = new User();
        user.setUsername("sfsfsfs");
        user.setPassword("sfsdffds");
        Role role = roleRepository.findById(2L).orElse(null);
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);
        assertNotEquals(user.getId(), 0);
    }
}