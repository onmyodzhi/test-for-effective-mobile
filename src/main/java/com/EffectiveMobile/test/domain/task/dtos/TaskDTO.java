package com.EffectiveMobile.test.domain.task.dtos;

import lombok.Value;

import java.time.OffsetDateTime;

/**
 * DTO for {@link com.EffectiveMobile.test.domain.task.entities.Task}
 */
@Value
public class TaskDTO {
    Long id;
    String title;
    String description;
    TaskStatusDTO status;
    TaskPriorityDTO priority;
    String assignedUserId;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}