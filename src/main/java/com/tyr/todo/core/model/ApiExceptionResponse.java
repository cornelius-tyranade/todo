package com.tyr.todo.core.model;

import lombok.*;

import java.time.ZonedDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiExceptionResponse {
    private String errorCode;
    private String errorMessage;
    private ZonedDateTime zonedDateTime;
}
