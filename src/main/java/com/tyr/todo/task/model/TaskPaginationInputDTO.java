package com.tyr.todo.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskPaginationInputDTO {
    private String username;
    private int currentPage;
    private int pageSize;
}
