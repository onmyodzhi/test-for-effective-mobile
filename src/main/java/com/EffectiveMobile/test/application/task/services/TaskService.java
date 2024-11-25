package com.EffectiveMobile.test.application.task.services;

import com.EffectiveMobile.test.application.task.specifications.TaskFilter;
import com.EffectiveMobile.test.domain.task.dtos.TaskDTO;
import com.EffectiveMobile.test.domain.task.entities.Task;
import com.EffectiveMobile.test.domain.task.mappers.TaskMapper;
import com.EffectiveMobile.test.exceptions.task.TaskNotFoundException;
import com.EffectiveMobile.test.infrastructure.task.persistence.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskService {

    final TaskRepository taskRepository;
    final TaskMapper taskMapper;

    public Page<TaskDTO> findAll(TaskFilter filter, int pages, int size, String sortOrder) {
        Specification<Task> spec = filter != null ? filter.getSpec() : null;

        Sort sort = Sort.by("createdAt");

        if ("desc".equalsIgnoreCase(sortOrder)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        Page<Task> tasksPage = taskRepository.findAll(spec, PageRequest.of(pages, size, sort));

        return tasksPage.map(taskMapper::toDto);
    }

    public TaskDTO findById(Long id) {
        log.info("find task by id {}", id);
        return taskRepository.findById(id)
                .map(taskMapper::toDto)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<TaskDTO> findByUserId(String id) {
        return taskRepository.findAllByAssignedUserId(id).stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Transactional
    public TaskDTO saveOrUpdate(TaskDTO taskDTO) {

        if (taskDTO.getId() == null) {
            Task task = taskMapper.toEntity(taskDTO);
            return save(task)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid task data"));
        }
        return update(taskDTO)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task data"));
    }

    @Transactional
    public void deleteById(Long id) {
        log.info("delete task by id {}", id);
        taskRepository.deleteById(id);
    }

    private Optional<TaskDTO> save(Task task) {
        log.info("save task {}", task);

        task = taskRepository.save(task);

        return Optional.ofNullable(taskMapper.toDto(task));
    }

    private Optional<TaskDTO> update(TaskDTO taskDTO) {
        log.info("update task {}", taskDTO);

        Task taskForUpdate = taskRepository.getReferenceById(taskDTO.getId());

        taskForUpdate = taskMapper.partialUpdate(taskDTO, taskForUpdate);

        return save(taskForUpdate);
    }
}
