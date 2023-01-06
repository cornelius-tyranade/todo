package com.tyr.todo.auth.service;


import com.tyr.todo.core.helper.FunctionHelper;
import com.tyr.todo.core.service.JwtService;
import com.tyr.todo.user.entity.User;
import com.tyr.todo.user.model.UserDTO;
import com.tyr.todo.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private FunctionHelper functionHelper;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    private AuthenticationService authenticationService;

    @BeforeEach
    public void init() {
        authenticationService = new AuthenticationService(userRepository, functionHelper, jwtService, authenticationManager);
    }

    @Test
    public void canSelectTasksByUsername() {
        String username = "test";
        String password = "pwd123";
        UserDTO userDTO = UserDTO.builder().username(username).password(password).build();
        User user = User.builder().username(username).build();

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.ofNullable(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("bearer token");

        authenticationService.authenticate(userDTO);

        verify(authenticationManager, times(1)).authenticate(new UsernamePasswordAuthenticationToken(username, password));
        verify(userRepository, times(1)).findByUsername(username);
    }
}