package com.tyr.todo.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskInputDTO {
    @NotEmpty
    @Size(min = 4, message = "Username should have at least 4 characters")
    @Size(max = 50, message = "Username should have maximum 50 characters")
    private String username;
    @NotEmpty
    @Size(min = 2, message = "Description should have at least 2 characters")
    @Size(max = 200, message = "Description should have maximum 200 characters")
    private String description;
    private Status status;

}
