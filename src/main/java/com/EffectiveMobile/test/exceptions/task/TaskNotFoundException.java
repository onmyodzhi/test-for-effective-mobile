package com.EffectiveMobile.test.exceptions.task;

import com.EffectiveMobile.test.exceptions.responses.task.TaskNotFoundResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends TaskException {

    public TaskNotFoundException(Long id) {
        super(new TaskNotFoundResponse("Task with id " + id + " not found", id), HttpStatus.NOT_FOUND);
    }
}
