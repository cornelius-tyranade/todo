package com.tyr.todo.user.service;

import com.tyr.todo.core.exception.ApiException;
import com.tyr.todo.core.helper.FunctionHelper;
import com.tyr.todo.core.model.ApiExceptionEnum;
import com.tyr.todo.user.entity.User;
import com.tyr.todo.user.model.UserDTO;
import com.tyr.todo.user.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private FunctionHelper functionHelper;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;

    private final String username = "test";

    @BeforeEach
    public void init() {
        userService = new UserService(userRepository, functionHelper, bCryptPasswordEncoder);
    }

    @Test
    public void canSelectUser() {
        when(userRepository.findByUsername(any(String.class)))
                .then((Answer<Optional<User>>) invocationOnMock -> Optional.of(User.builder().username(username).build()));

        userService.selectUser(UserDTO.builder().username(username).build());

        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void willThrowWhenSelectUserWithInvalidUsername() {
        ApiException apiException = ApiException.builder()
                .errorCode(ApiExceptionEnum.NOT_FOUND.getErrorCode())
                .errorMessage(ApiExceptionEnum.NOT_FOUND.getErrorMessage())
                .build();

        when(functionHelper.constructApiException(ApiExceptionEnum.NOT_FOUND)).thenReturn(apiException);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.selectUser(UserDTO.builder().username(username).build()))
                .isInstanceOf(ApiException.class)
                .extracting("errorMessage")
                .isEqualTo("Record was not found");
    }

    @Test
    public void canAddUser() {
        when(functionHelper.convert(any(UserDTO.class), any(Class.class))).thenReturn(User.builder().username(username).build());
        when(userRepository.findByUsername(any(String.class)))
                .then((Answer<Optional<User>>) invocationOnMock -> Optional.empty());

        userService.addUser(UserDTO.builder().username(username).build());

        verify(functionHelper, times(1)).convert(UserDTO.builder().username(username).build(), User.class);
        verify(userRepository, times(1)).findByUsername(username);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getUsername(), username);
    }

    @Test
    public void willThrowWhenAddUserWithExistingUsername() {
        ApiException apiException = ApiException.builder()
                .errorCode(ApiExceptionEnum.USER_EXIST.getErrorCode())
                .errorMessage(ApiExceptionEnum.USER_EXIST.getErrorMessage())
                .build();

        when(functionHelper.convert(any(UserDTO.class), any(Class.class))).thenReturn(User.builder().username(username).build());
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(User.builder().username(username).build()));
        when(functionHelper.constructApiException(ApiExceptionEnum.USER_EXIST)).thenReturn(apiException);

        assertThatThrownBy(() -> userService.addUser(UserDTO.builder().username(username).build()))
                .isInstanceOf(ApiException.class)
                .extracting("errorMessage")
                .isEqualTo("User already exist");
    }

    @Test
    public void canUpdateUser() {
        when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn("encoded_password");
        when(userRepository.findByUsername(any(String.class)))
                .then((Answer<Optional<User>>) invocationOnMock -> Optional.of(User.builder().username(username).build()));

        userService.updateUser(UserDTO.builder().username(username).password("updated_password").build());

        verify(bCryptPasswordEncoder, times(1)).encode("updated_password");
        verify(userRepository, times(1)).findByUsername(username);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getUsername(), username);
    }

    @Test
    public void willThrowWhenUpdateUserWithInvalidUsername() {
        ApiException apiException = ApiException.builder()
                .errorCode(ApiExceptionEnum.NOT_FOUND.getErrorCode())
                .errorMessage(ApiExceptionEnum.NOT_FOUND.getErrorMessage())
                .build();

        when(functionHelper.constructApiException(ApiExceptionEnum.NOT_FOUND)).thenReturn(apiException);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUser(UserDTO.builder().username(username).build()))
                .isInstanceOf(ApiException.class)
                .extracting("errorMessage")
                .isEqualTo("Record was not found");
    }

    @Test
    public void canDeleteUser() {
        when(userRepository.findByUsername(any(String.class)))
                .then((Answer<Optional<User>>) invocationOnMock -> Optional.of(User.builder().username(username).build()));

        userService.deleteUser(UserDTO.builder().username(username).build());

        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).delete(User.builder().username(username).build());
    }

    @Test
    public void willThrowWhenDeleteUserWithInvalidUsername() {
        ApiException apiException = ApiException.builder()
                .errorCode(ApiExceptionEnum.NOT_FOUND.getErrorCode())
                .errorMessage(ApiExceptionEnum.NOT_FOUND.getErrorMessage())
                .build();

        when(functionHelper.constructApiException(ApiExceptionEnum.NOT_FOUND)).thenReturn(apiException);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.deleteUser(UserDTO.builder().username(username).build()))
                .isInstanceOf(ApiException.class)
                .extracting("errorMessage")
                .isEqualTo("Record was not found");
    }

}