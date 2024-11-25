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
@Table(name = "task_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskStatus {

    @Id
    @Column(name = "name", nullable = false, unique = true)
    @NotNull(message = "Status name is required")
    @NotBlank(message = "Status can`t be empty")
    @Size(max = 50, message = "Status name shouldn't be longer than 50 characters")
    String name;
}
