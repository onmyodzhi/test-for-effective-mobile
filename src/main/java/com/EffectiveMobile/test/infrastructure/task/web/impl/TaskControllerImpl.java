package com.EffectiveMobile.test.infrastructure.task.web.impl;

import com.EffectiveMobile.test.application.task.services.TaskService;
import com.EffectiveMobile.test.application.task.specifications.TaskFilter;
import com.EffectiveMobile.test.domain.task.dtos.TaskDTO;
import com.EffectiveMobile.test.infrastructure.task.web.TaskController;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskControllerImpl implements TaskController {

    TaskService taskService;


    @Override
    public ResponseEntity<Page<TaskDTO>> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortOrder,
            TaskFilter taskFilter) {

        Page<TaskDTO> tasks = taskService.findAll(taskFilter, page, size, sortOrder);
        return ResponseEntity.ok(tasks);
    }

    @Override
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long id) {
        TaskDTO task = taskService.findById(id);

        return ResponseEntity.ok(task);
    }

    @Override
    public ResponseEntity<List<TaskDTO>> getUsersTasks(String id) {
        List<TaskDTO> tasks = taskService.findByUserId(id);

        return ResponseEntity.ok(tasks);
    }

    @Override
    public ResponseEntity<Void> createTask(TaskDTO taskDTO) {
        TaskDTO savedTask = taskService.saveOrUpdate(taskDTO);

        return ResponseEntity.created(URI.create("/v1/task/" + savedTask.getId())).build();
    }

    @Override
    public ResponseEntity<TaskDTO> updateTask(TaskDTO taskDTO) {
        TaskDTO updatedTask = taskService.saveOrUpdate(taskDTO);

        return ResponseEntity.ok(updatedTask);
    }

    @Override
    public ResponseEntity<Void> deleteTask(Long id) {
        taskService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
