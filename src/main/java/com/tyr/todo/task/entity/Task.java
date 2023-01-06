package com.tyr.todo.task.entity;

import com.tyr.todo.task.model.Status;
import com.tyr.todo.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_mt_task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "create_dt")
    private OffsetDateTime createDate;
    @Column(name = "update_dt")
    private OffsetDateTime updateDate;
}
