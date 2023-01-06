package com.tyr.todo.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private UUID id;
    @NotEmpty
    @Size(min = 2, message = "Description should have at least 2 characters")
    @Size(max = 200, message = "Description should have maximum 200 characters")
    private String description;
    private Status status;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

}
