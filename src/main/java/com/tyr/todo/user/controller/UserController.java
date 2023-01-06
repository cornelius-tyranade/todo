package com.tyr.todo.user.controller;

import com.tyr.todo.user.entity.User;
import com.tyr.todo.user.model.UserDTO;
import com.tyr.todo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("retrieve")
    public User selectUser(@RequestBody UserDTO userDTO) {
        return userService.selectUser(userDTO);
    }

    @PostMapping("add")
    public User addUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @PostMapping("update")
    public User updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    @PostMapping("delete")
    public User deleteUser(@RequestBody UserDTO userDTO) {
        return userService.deleteUser(userDTO);
    }
}
