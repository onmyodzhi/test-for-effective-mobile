package com.EffectiveMobile.test.application.task.specifications;

import com.EffectiveMobile.test.domain.task.entities.Task;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class TaskFilter {

    Specification<Task> spec;
    final String filterParams;

    public TaskFilter(Map<String, String> params) {
        spec = Specification.where(null);
        StringBuilder paramsBuilder = new StringBuilder();

        String statusParam = params.get("status");
        if (statusParam != null && !statusParam.isEmpty()) {
            spec = spec.and(TaskSpecifications.hasStatus(statusParam));
            paramsBuilder.append("&status=").append(statusParam);
        }

        String priorityParam = params.get("priority");
        if (priorityParam != null && !priorityParam.isEmpty()) {
            spec = spec.and(TaskSpecifications.hasPriority(priorityParam));
            paramsBuilder.append("&priority=").append(priorityParam);
        }

        String assignedUserIdParam = params.get("assignedUserId");
        if (assignedUserIdParam != null && !assignedUserIdParam.isEmpty()) {
            spec = spec.and(TaskSpecifications.assignedToUser(assignedUserIdParam));
            paramsBuilder.append("&assignedUserId=").append(assignedUserIdParam);
        }

        String createdBeforeParam = params.get("createdBefore");
        if (createdBeforeParam != null && !createdBeforeParam.isEmpty()) {
            OffsetDateTime createdBefore = OffsetDateTime.parse(createdBeforeParam);
            spec = spec.and(TaskSpecifications.createdBefore(createdBefore));
            paramsBuilder.append("&createdBefore=").append(createdBeforeParam);
        }

        String createdAfterParam = params.get("createdAfter");
        if (createdAfterParam != null && !createdAfterParam.isEmpty()) {
            OffsetDateTime createdAfter = OffsetDateTime.parse(createdAfterParam);
            spec = spec.and(TaskSpecifications.createdAfter(createdAfter));
            paramsBuilder.append("&createdAfter=").append(createdAfterParam);
        }

        filterParams = paramsBuilder.toString();
    }
}

