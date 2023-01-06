package com.tyr.todo.auth.controller;

import com.tyr.todo.auth.model.AuthenticationDTO;
import com.tyr.todo.auth.service.AuthenticationService;
import com.tyr.todo.user.model.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("authenticate")
    public AuthenticationDTO authenticate(@RequestBody UserDTO userDTO) {
        return authenticationService.authenticate(userDTO);
    }
}
