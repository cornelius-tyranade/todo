package com.tyr.todo.task.controller;

import com.tyr.todo.task.model.TaskDTO;
import com.tyr.todo.task.model.TaskInputDTO;
import com.tyr.todo.task.model.TaskPaginationDTO;
import com.tyr.todo.task.model.TaskPaginationInputDTO;
import com.tyr.todo.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/v1/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;

    @GetMapping("inquiry/all")
    public TaskPaginationDTO selectAllTasks(@RequestBody TaskPaginationInputDTO taskPaginationInputDTO, @RequestAttribute("extractedUsername") String username) {
        taskPaginationInputDTO.setUsername(username);
        return taskService.selectTasksByUsername(taskPaginationInputDTO);
    }

    @PostMapping("retrieve")
    public TaskDTO selectTask(@RequestBody TaskDTO taskDTO) {
        return taskService.selectTask(taskDTO);
    }

    @PostMapping("add")
    public TaskDTO addTask(@RequestBody TaskInputDTO taskInputDTO, @RequestAttribute("extractedUsername") String username) {
        taskInputDTO.setUsername(username);
        return taskService.addTask(taskInputDTO);
    }

    @PostMapping("update")
    public TaskDTO updateTask(@RequestBody TaskDTO taskDTO) {
        return taskService.updateTask(taskDTO);
    }

    @PostMapping("delete")
    public TaskDTO deleteTask(@RequestBody TaskDTO taskDTO) {
        return taskService.deleteTask(taskDTO);
    }
}
