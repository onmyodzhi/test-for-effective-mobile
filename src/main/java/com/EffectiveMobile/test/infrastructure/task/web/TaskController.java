package com.EffectiveMobile.test.infrastructure.task.web;

import com.EffectiveMobile.test.application.task.specifications.TaskFilter;
import com.EffectiveMobile.test.domain.task.dtos.TaskDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface TaskController {

    @Operation(
            summary = "Get paginated and filtered list of tasks",
            description = "Returns a paginated list of tasks with optional filtering",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of tasks returned",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDTO.class)
                            )
                    )
            }
    )
    @GetMapping("/tasks")
    ResponseEntity<Page<TaskDTO>> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortOrder,
            TaskFilter taskFilter);

    @Operation(
            summary = "Get task by ID",
            description = "Returns a task by the provided ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Task not found"
                    )
            }
    )
    @GetMapping("/task/{id}")
    ResponseEntity<TaskDTO> getTask(@Parameter(description = "Task ID", required = true) @PathVariable Long id);

    @Operation(
            summary = "Get tasks by user ID",
            description = "Returns a list of tasks assigned to the user with the provided ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of user's tasks returned",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User or tasks not found"
                    )
            }
    )
    @GetMapping("/user/{id}/tasks")
    ResponseEntity<List<TaskDTO>> getUsersTasks(@Parameter(description = "User ID", required = true) @PathVariable String id);

    @Operation(
            summary = "Create a new task",
            description = "Creates a new task in the system",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Task created"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid task data"
                    )
            }
    )
    @PostMapping("/task")
    ResponseEntity<Void> createTask(@RequestBody @Valid TaskDTO taskDTO);

    @Operation(
            summary = "Update task information",
            description = "Updates the information of an existing task",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid task data"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Task not found"
                    )
            }
    )
    @PatchMapping("/task/{id}")
    ResponseEntity<TaskDTO> updateTask(@RequestBody @Valid TaskDTO taskDTO);

    @Operation(
            summary = "Delete task by ID",
            description = "Deletes the task with the provided ID",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Task deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Task not found"
                    )
            }
    )
    @DeleteMapping("/task/{id}")
    ResponseEntity<Void> deleteTask(@Parameter(description = "Task ID", required = true) @PathVariable Long id);
}
