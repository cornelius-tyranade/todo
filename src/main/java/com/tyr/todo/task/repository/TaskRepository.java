package com.tyr.todo.task.repository;

import com.tyr.todo.task.entity.Task;
import com.tyr.todo.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    Optional<Task> findById(UUID id);
    Page<Task> findByUser(User user, Pageable pageable);
}
