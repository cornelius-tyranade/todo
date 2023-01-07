package com.tyr.todo.auth.service;

import com.tyr.todo.auth.model.AuthenticationDTO;
import com.tyr.todo.core.helper.FunctionHelper;
import com.tyr.todo.core.model.ApiExceptionEnum;
import com.tyr.todo.core.service.JwtService;
import com.tyr.todo.user.entity.User;
import com.tyr.todo.user.model.UserDTO;
import com.tyr.todo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final FunctionHelper functionHelper;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationDTO authenticate(UserDTO userDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getUsername(),
                        userDTO.getPassword()
                )
        );

        User user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> {
                    log.error("User with username " + userDTO.getUsername() + " was not found.");
                    return functionHelper.constructApiException(ApiExceptionEnum.NOT_FOUND);
                });

        return AuthenticationDTO.builder()
                .token(jwtService.generateToken(user))
                .name(user.getFirstName() + " " + user.getLastName())
                .role(user.getRole())
                .build();
    }
}
