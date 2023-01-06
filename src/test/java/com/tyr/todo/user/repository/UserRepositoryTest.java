package com.tyr.todo.user.repository;

import com.tyr.todo.user.entity.User;
import com.tyr.todo.user.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private final String username = "test";

    @BeforeEach
    public void init() {
        userRepository.save(User
                .builder()
                .username(username)
                .password("pwd123")
                .firstName("Test")
                .lastName("One")
                .role(Role.USER)
                .build()
        );
    }

    @Test
    public void shouldReturnUserDetailWhenQueryByUsername() {
        // Username test
        User user = User.builder().build();

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }

        assertEquals(user.getUsername(), username);
    }

    @Test
    public void shouldReturnNullWhenQueryByUsername() {
        // Username test
        User user = null;

        Optional<User> optionalUser = userRepository.findByUsername("unknown");

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }

        assertNull(user);
    }

}