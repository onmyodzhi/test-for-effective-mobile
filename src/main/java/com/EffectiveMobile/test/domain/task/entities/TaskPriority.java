package com.EffectiveMobile.test.domain.task.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "task_priorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskPriority {

    @Id
    @Column(name = "name", nullable = false, unique = true)
    @NotNull(message = "Priority name is required")
    @NotBlank(message = "Priority can`t be empty")
    @Size(max = 50, message = "Priority name shouldn't be longer than 50 characters")
    String name;
}
