package com.tyr.todo.task.service;

import com.tyr.todo.core.helper.FunctionHelper;
import com.tyr.todo.task.entity.Task;
import com.tyr.todo.task.model.Status;
import com.tyr.todo.task.model.TaskDTO;
import com.tyr.todo.task.model.TaskInputDTO;
import com.tyr.todo.task.model.TaskPaginationInputDTO;
import com.tyr.todo.task.repository.TaskRepository;
import com.tyr.todo.user.entity.User;
import com.tyr.todo.user.model.UserDTO;
import com.tyr.todo.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private FunctionHelper functionHelper;
    @Mock
    private UserService userService;
    private TaskService taskService;

    private final String username = "test";

    @BeforeEach
    public void init() {
        taskService = new TaskService(taskRepository, userService, functionHelper);
    }

    @Test
    public void canSelectTasksByUsername() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(Task.builder().build());
        tasks.add(Task.builder().build());
        Page<Task> pageTask = new PageImpl<>(tasks);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createDate").descending());
        List<TaskDTO> taskDtos = new ArrayList<>();
        taskDtos.add(TaskDTO.builder().build());
        taskDtos.add(TaskDTO.builder().build());

        when(userService.selectUser(any(UserDTO.class))).thenReturn(User.builder().username(username).build());
        when(taskRepository.findByUser(any(User.class), any(Pageable.class))).thenReturn(pageTask);
        when(functionHelper.mapList(any(List.class), any(Class.class))).thenReturn(taskDtos);

        taskService.selectTasksByUsername(TaskPaginationInputDTO.builder().currentPage(0).pageSize(20).username(username).build());

        verify(taskRepository, times(1)).findByUser(User.builder().username(username).build(), pageable);
        verify(functionHelper, times(1)).mapList(tasks, TaskDTO.class);
    }

    @Test
    public void canSelectTask() {
        UUID uuid = UUID.randomUUID();

        when(taskRepository.findById(any(UUID.class))).thenReturn(Optional.of(Task.builder().id(uuid).build()));

        taskService.selectTask(TaskDTO.builder().id(uuid).description("Buy Milk").status(Status.UNCHECKED).build());

        verify(taskRepository, times(1)).findById(uuid);
    }

    @Test
    public void canAddTask() {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse("2022-06-01T00:00:00+00:00");
        User user = User.builder().username(username).build();
        Task task = Task.builder().user(user).description("Buy Milk").status(Status.UNCHECKED).createDate(offsetDateTime).build();

        when(userService.selectUser(any(UserDTO.class))).thenReturn(user);
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(functionHelper.getOffsetDateTime()).thenReturn(offsetDateTime);

        taskService.addTask(TaskInputDTO.builder().username(username).description("Buy Milk").status(Status.UNCHECKED).build());

        verify(userService, times(1)).selectUser(UserDTO.builder().username(username).build());
        verify(functionHelper, times(1)).getOffsetDateTime();
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void canUpdateTask() {
        UUID uuid = UUID.randomUUID();
        OffsetDateTime offsetDateTime = OffsetDateTime.parse("2022-06-01T00:00:00+00:00");
        User user = User.builder().username(username).build();
        Task task = Task.builder().user(user).description("Buy Milk").status(Status.UNCHECKED).createDate(offsetDateTime).build();

        when(taskRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(functionHelper.getOffsetDateTime()).thenReturn(offsetDateTime);

        taskService.updateTask(TaskDTO.builder().id(uuid).build());

        verify(taskRepository, times(1)).findById(uuid);
        verify(functionHelper, times(1)).getOffsetDateTime();
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void canDeleteTask() {
        UUID uuid = UUID.randomUUID();
        OffsetDateTime offsetDateTime = OffsetDateTime.parse("2022-06-01T00:00:00+00:00");
        User user = User.builder().username(username).build();
        Task task = Task.builder().user(user).description("Buy Milk").status(Status.UNCHECKED).createDate(offsetDateTime).build();

        when(taskRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(task));

        taskService.deleteTask(TaskDTO.builder().id(uuid).build());

        verify(taskRepository, times(1)).findById(uuid);
        verify(taskRepository, times(1)).delete(task);
    }

}