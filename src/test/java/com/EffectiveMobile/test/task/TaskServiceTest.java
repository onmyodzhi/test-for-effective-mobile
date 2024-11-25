package com.EffectiveMobile.test.task;

import com.EffectiveMobile.test.application.task.services.TaskService;
import com.EffectiveMobile.test.application.task.specifications.TaskFilter;
import com.EffectiveMobile.test.domain.task.dtos.TaskDTO;
import com.EffectiveMobile.test.domain.task.dtos.TaskPriorityDTO;
import com.EffectiveMobile.test.domain.task.dtos.TaskStatusDTO;
import com.EffectiveMobile.test.domain.task.entities.Task;
import com.EffectiveMobile.test.domain.task.entities.TaskPriority;
import com.EffectiveMobile.test.domain.task.entities.TaskStatus;
import com.EffectiveMobile.test.domain.task.mappers.TaskMapper;
import com.EffectiveMobile.test.exceptions.responses.task.TaskNotFoundResponse;
import com.EffectiveMobile.test.exceptions.task.TaskNotFoundException;
import com.EffectiveMobile.test.infrastructure.task.persistence.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskService(taskRepository, taskMapper);
    }

    @Test
    void testFindAll() {
        Task task = new Task(1L, "Test Task", "Description", new TaskStatus("InProgress"), new TaskPriority("High"), "user1", null, null);
        TaskDTO taskDTO = new TaskDTO(1L, "Test Task", "Description", new TaskStatusDTO("InProgress"), new TaskPriorityDTO("High"), "user1", null, null);

        TaskFilter filter = mock(TaskFilter.class);
        when(filter.getSpec()).thenReturn(Specification.where(null));

        Page<Task> taskPage = new PageImpl<>(List.of(task));

        when(taskRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(taskPage);
        when(taskMapper.toDto(any(Task.class))).thenReturn(taskDTO);

        Page<TaskDTO> tasks = taskService.findAll(filter, 0, 10, "asc");

        assertNotNull(tasks);
        assertEquals(1, tasks.getTotalElements());
        assertEquals("Test Task", tasks.getContent().get(0).getTitle());
    }

    @Test
    void testFindById() {
        Task task = new Task(1L, "Test Task", "Description", new TaskStatus("InProgress"), new TaskPriority("High"), "user1", null, null);
        TaskDTO taskDTO = new TaskDTO(1L, "Test Task", "Description", new TaskStatusDTO("InProgress"), new TaskPriorityDTO("High"), "user1", null, null);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(taskDTO);

        TaskDTO result = taskService.findById(1L);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
    }

    @Test
    void testFindByIdNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> {
            taskService.findById(1L);
        });

        TaskNotFoundResponse response = (TaskNotFoundResponse) exception.getPayload();
        assertEquals("Task with id 1 not found", response.getMessage());
        assertEquals(1L, response.getId());
    }


    @Test
    void testFindByUserId() {
        Task task1 = new Task(1L, "Test Task 1", "Description", new TaskStatus("InProgress"), new TaskPriority("High"), "user1", null, null);
        Task task2 = new Task(2L, "Test Task 2", "Description", new TaskStatus("completed"), new TaskPriority("low"), "user1", null, null);
        TaskDTO taskDTO1 = new TaskDTO(1L, "Test Task 1", "Description", new TaskStatusDTO("InProgress"), new TaskPriorityDTO("High"), "user1", null, null);
        TaskDTO taskDTO2 = new TaskDTO(2L, "Test Task 2", "Description", new TaskStatusDTO("InProgress"), new TaskPriorityDTO("High"), "user1", null, null);

        when(taskRepository.findAllByAssignedUserId("user1")).thenReturn(List.of(task1, task2));
        when(taskMapper.toDto(task1)).thenReturn(taskDTO1);
        when(taskMapper.toDto(task2)).thenReturn(taskDTO2);

        List<TaskDTO> tasks = taskService.findByUserId("user1");

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertEquals("Test Task 1", tasks.get(0).getTitle());
        assertEquals("Test Task 2", tasks.get(1).getTitle());
    }

    @Test
    void testSaveOrUpdateNewTask() {
        TaskDTO taskDTO = new TaskDTO(null, "New Task", "Description", new TaskStatusDTO("InProgress"), new TaskPriorityDTO("High"), "user1", null, null);
        Task task = new Task(null, "New Task", "Description", new TaskStatus("InProgress"), new TaskPriority("High"), "user1", null, null);
        TaskDTO savedTaskDTO = new TaskDTO(1L, "New Task", "Description", new TaskStatusDTO("InProgress"), new TaskPriorityDTO("High"), "user1", null, null);

        when(taskMapper.toEntity(taskDTO)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(savedTaskDTO);

        TaskDTO result = taskService.saveOrUpdate(taskDTO);

        assertNotNull(result);
        assertEquals("New Task", result.getTitle());
    }

    @Test
    void testSaveOrUpdateExistingTask() {
        TaskDTO taskDTO = new TaskDTO(1L, "Updated Task", "Updated Description", new TaskStatusDTO("Completed"), new TaskPriorityDTO("Low"), "user1", null, null);
        Task task = new Task(1L, "Updated Task", "Updated Description", new TaskStatus("Completed"), new TaskPriority("Low"), "user1", null, null);
        TaskDTO updatedTaskDTO = new TaskDTO(1L, "Updated Task", "Updated Description", new TaskStatusDTO("Completed"), new TaskPriorityDTO("Low"), "user1", null, null);

        when(taskRepository.getReferenceById(1L)).thenReturn(task);
        when(taskMapper.partialUpdate(taskDTO, task)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(updatedTaskDTO);

        TaskDTO result = taskService.saveOrUpdate(taskDTO);

        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
    }

    @Test
    void testDeleteById() {
        TaskDTO taskDTO = new TaskDTO(1L, "Task to Delete", "Description", new TaskStatusDTO("InProgress"), new TaskPriorityDTO("High"), "user1", null, null);

        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteById(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }
}