package com.tyr.todo.core.exception;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiException extends RuntimeException {
    private String errorCode;
    private String errorMessage;
}
