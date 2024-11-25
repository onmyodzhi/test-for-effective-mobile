package com.EffectiveMobile.test.domain.task.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_id_seq")
    @SequenceGenerator(name = "tasks_id_seq", sequenceName = "tasks_id_seq", allocationSize = 1)
    Long id;

    @NotNull(message = "Title is required")
    @NotBlank(message = "Title shouldn`t be empty")
    @Column(name = "title")
    @Size(max = 255, message = "Title shouldn`t be longer than 255 characters")
    String title;

    @Lob
    @Column(name = "description")
    String description;

    @NotNull(message = "Status is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_name", nullable = false)
    TaskStatus status;

    @NotNull(message = "Priority is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_name", nullable = false)
    TaskPriority priority;

    @Column(name = "assigned_user_id")
    String assignedUserId;

    @NotNull(message = "Created at is required")
    @Column(name = "created_at", updatable = false)
    OffsetDateTime createdAt;

    @NotNull(message = "Updated at is required")
    @Column(name = "updated_at")
    OffsetDateTime updatedAt;
}