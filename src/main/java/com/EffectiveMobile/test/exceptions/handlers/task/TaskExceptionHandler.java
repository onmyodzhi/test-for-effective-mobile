package com.EffectiveMobile.test.exceptions.handlers.task;

import com.EffectiveMobile.test.exceptions.responses.MessageResponse;
import com.EffectiveMobile.test.exceptions.task.TaskException;
import com.fasterxml.jackson.core.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class TaskExceptionHandler {
    public ResponseEntity<MessageResponse> handleJsonParseException(JsonParseException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(TaskException.class)
    public ResponseEntity<?> handleWalletException(TaskException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(ex.getPayload());
    }
}
