package com.tyr.todo.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiExceptionEnum {
    NOT_FOUND("9001", "Record was not found"),
    USER_EXIST("9002", "User already exist");

    private final String errorCode;
    private final String errorMessage;
}
