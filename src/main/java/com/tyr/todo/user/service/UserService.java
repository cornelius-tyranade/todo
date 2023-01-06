package com.tyr.todo.user.service;

import com.tyr.todo.core.helper.FunctionHelper;
import com.tyr.todo.core.model.ApiExceptionEnum;
import com.tyr.todo.user.entity.User;
import com.tyr.todo.user.model.UserDTO;
import com.tyr.todo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FunctionHelper functionHelper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User selectUser(UserDTO userDTO) {
        return userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> {
                    log.error("User with username " + userDTO.getUsername() + " was not found.");
                    return functionHelper.constructApiException(ApiExceptionEnum.NOT_FOUND);
                });
    }

    public User addUser(UserDTO userDTO) {
        User user = functionHelper.convert(userDTO, User.class);
        userRepository.findByUsername(user.getUsername()).ifPresent(item -> {
            log.error("User with username " + userDTO.getUsername() + " already exist.");
            throw functionHelper.constructApiException(ApiExceptionEnum.USER_EXIST);
        });
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setRole(userDTO.getRole());
        return userRepository.save(user);
    }

    public User updateUser(UserDTO userDTO) {
        User userByUsername = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> {
                    log.error("User with username " + userDTO.getUsername() + " was not found.");
                    return functionHelper.constructApiException(ApiExceptionEnum.NOT_FOUND);
                });
        userByUsername.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userByUsername.setFirstName(userDTO.getFirstName());
        userByUsername.setLastName(userDTO.getLastName());
        userByUsername.setRole(userDTO.getRole());
        return userRepository.save(userByUsername);
    }

    public User deleteUser(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> {
                    log.error("User with username " + userDTO.getUsername() + " was not found.");
                    return functionHelper.constructApiException(ApiExceptionEnum.NOT_FOUND);
                });
        userRepository.delete(user);
        return user;
    }
}
