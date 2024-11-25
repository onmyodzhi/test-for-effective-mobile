package com.EffectiveMobile.test.exceptions.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class TaskException extends RuntimeException {

    HttpStatus status;
    Object payload;

    public TaskException(Object payload, HttpStatus status) {
        super(payload.toString());
        this.status = status;
        this.payload = payload;
    }
}
