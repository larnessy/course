package com.course.repository.jpa.security;

import com.course.model.entity.security.Role;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Transactional
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    void findUserByUsername() {
        Role role = roleRepository.findById(1L).orElse(null);

        assertNotNull(role);
        assertNotNull(role.getId());
        assertNotNull(role.getName());
        assertNotEquals(role.getUsers().isEmpty(), true);
    }
}