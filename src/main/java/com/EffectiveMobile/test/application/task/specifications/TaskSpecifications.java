package com.EffectiveMobile.test.application.task.specifications;

import com.EffectiveMobile.test.domain.task.entities.Task;
import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;

public class TaskSpecifications {

    public static Specification<Task> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status").get("name"), status);
    }

    public static Specification<Task> hasPriority(String priority) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("priority").get("name"), priority);
    }

    public static Specification<Task> assignedToUser(String userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("assignedUserId"), userId);
    }

    public static Specification<Task> createdBefore(OffsetDateTime dateTime) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), dateTime);
    }

    public static Specification<Task> createdAfter(OffsetDateTime dateTime) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), dateTime);
    }
}
