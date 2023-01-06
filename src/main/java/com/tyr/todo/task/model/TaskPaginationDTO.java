package com.tyr.todo.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskPaginationDTO {
    private List<TaskDTO> result;
    private int currentPage;
    private int totalPage;
    private int pageSize;
    private long totalRecord;
}
