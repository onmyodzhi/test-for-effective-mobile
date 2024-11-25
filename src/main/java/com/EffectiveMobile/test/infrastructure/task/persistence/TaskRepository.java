package com.EffectiveMobile.test.infrastructure.task.persistence;

import com.EffectiveMobile.test.domain.task.entities.Task;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    @EntityGraph(attributePaths = {"status", "priority"})
    List<Task> findAllByAssignedUserId(String assignedUserId);
}
