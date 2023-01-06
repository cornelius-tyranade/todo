package com.tyr.todo.task.repository;

import com.tyr.todo.task.entity.Task;
import com.tyr.todo.task.model.Status;
import com.tyr.todo.user.entity.User;
import com.tyr.todo.user.model.Role;
import com.tyr.todo.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;
    private final String taskDescription1 = "Buy Milk";
    private UUID savedUserId;
    private UUID savedTaskId;



    @BeforeEach
    public void init() {
        String username = "test";
        User user = userRepository.save(User
                .builder()
                .username(username)
                .password("pwd123")
                .firstName("Test")
                .lastName("One")
                .role(Role.USER)
                .build()
        );

        savedUserId = user.getId();

        Task task = taskRepository.save(
                Task.builder()
                        .user(user)
                        .description(taskDescription1)
                        .status(Status.UNCHECKED)
                        .build());

        savedTaskId = task.getId();

        taskRepository.save(
                Task.builder()
                        .user(user)
                        .description("Buy Cheese")
                        .status(Status.UNCHECKED)
                        .build());
    }

    @Test
    public void shouldReturnTasksWhenQueryByUsername() {
        // Username test
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createDate").descending());

        List<Task> tasks = taskRepository.findAll(pageable).getContent().stream()
                .filter(t -> t.getUser().getId().equals(savedUserId)).collect(Collectors.toList());

        assertEquals(tasks.size(), 2);
    }

    @Test
    public void shouldReturnSizeZeroWhenQueryByUsername() {
        // Username test
        String notValidId = "00000000-0000-0000-0000-000000000000";
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createDate").descending());

        List<Task> tasks = taskRepository.findAll(pageable).getContent().stream()
                .filter(t -> t.getUser().getId().equals(notValidId)).collect(Collectors.toList());

        assertEquals(tasks.size(), 0);
    }

    @Test
    public void shouldReturnTaskWhenQueryByTaskId() {
        // savedTaskId
        Task task = Task.builder().build();

        Optional<Task> optionalTask = taskRepository.findById(savedTaskId);
        if (optionalTask.isPresent()) {
            task = optionalTask.get();
        }

        assertEquals(task.getDescription(), taskDescription1);
    }

    @Test
    public void shouldReturnNullWhenQueryByTaskId() {
        // savedTaskId
        String notValidId = "00000000-0000-0000-0000-000000000000";
        Task task = null;

        Optional<Task> optionalTask = taskRepository.findById(UUID.fromString(notValidId));
        if (optionalTask.isPresent()) {
            task = optionalTask.get();
        }

        assertNull(task);
    }
}