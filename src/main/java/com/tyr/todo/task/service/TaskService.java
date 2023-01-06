package com.tyr.todo.task.service;

import com.tyr.todo.core.helper.FunctionHelper;
import com.tyr.todo.core.model.ApiExceptionEnum;
import com.tyr.todo.task.entity.Task;
import com.tyr.todo.task.model.TaskDTO;
import com.tyr.todo.task.model.TaskInputDTO;
import com.tyr.todo.task.model.TaskPaginationDTO;
import com.tyr.todo.task.model.TaskPaginationInputDTO;
import com.tyr.todo.task.repository.TaskRepository;
import com.tyr.todo.user.entity.User;
import com.tyr.todo.user.model.UserDTO;
import com.tyr.todo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final UserService userService;
    private final FunctionHelper functionHelper;

    public TaskPaginationDTO selectTasksByUsername(TaskPaginationInputDTO taskPaginationInputDTO) {
        Pageable pageable = PageRequest.of(taskPaginationInputDTO.getCurrentPage(), taskPaginationInputDTO.getPageSize(), Sort.by("createDate").descending());
        Page<Task> taskList = taskRepository.findByUser(
                userService.selectUser(UserDTO.builder().username(taskPaginationInputDTO.getUsername()).build()),
                pageable
        );
        List<TaskDTO> taskDTOList = functionHelper.mapList(taskList.getContent(), TaskDTO.class);

        return TaskPaginationDTO.builder()
                .result(taskDTOList)
                .currentPage(taskList.getNumber() + 1)
                .totalPage(taskList.getTotalPages())
                .pageSize(taskList.getSize())
                .totalRecord(taskList.getTotalElements())
                .build();
    }

    public TaskDTO selectTask(TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskDTO.getId())
                .orElseThrow(() -> {
                    log.error("Task with ID " + taskDTO.getId() + " was not found.");
                    return functionHelper.constructApiException(ApiExceptionEnum.NOT_FOUND);
                });
        return TaskDTO.builder()
                .id(task.getId())
                .description(task.getDescription())
                .status(task.getStatus())
                .createDate(task.getCreateDate())
                .updateDate(task.getUpdateDate())
                .build();
    }

    public TaskDTO addTask(TaskInputDTO taskInputDTO) {
        User user = userService.selectUser(UserDTO.builder().username(taskInputDTO.getUsername()).build());
        Task task = taskRepository.save(Task.builder()
                .user(user)
                .description(taskInputDTO.getDescription())
                .status(taskInputDTO.getStatus())
                .createDate(functionHelper.getOffsetDateTime())
                .build());
        return TaskDTO.builder()
                .id(task.getId())
                .description(task.getDescription())
                .status(task.getStatus())
                .createDate(task.getCreateDate())
                .updateDate(task.getUpdateDate())
                .build();
    }

    public TaskDTO updateTask(TaskDTO taskDTO) {
        Task taskById = taskRepository.findById(taskDTO.getId())
                .orElseThrow(() -> {
                    log.error("Task with ID " + taskDTO.getId() + " was not found.");
                    return functionHelper.constructApiException(ApiExceptionEnum.NOT_FOUND);
                });
        taskById.setDescription(taskDTO.getDescription());
        taskById.setStatus(taskDTO.getStatus());
        taskById.setUpdateDate(functionHelper.getOffsetDateTime());
        Task task = taskRepository.save(taskById);
        return TaskDTO.builder()
                .id(task.getId())
                .description(task.getDescription())
                .status(task.getStatus())
                .createDate(task.getCreateDate())
                .updateDate(task.getUpdateDate())
                .build();
    }

    public TaskDTO deleteTask(TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskDTO.getId())
                .orElseThrow(() -> {
                    log.error("Task with ID " + taskDTO.getId() + " was not found.");
                    return functionHelper.constructApiException(ApiExceptionEnum.NOT_FOUND);
                });
        taskRepository.delete(task);
        return TaskDTO.builder()
                .id(task.getId())
                .description(task.getDescription())
                .status(task.getStatus())
                .createDate(task.getCreateDate())
                .updateDate(task.getUpdateDate())
                .build();
    }
}
