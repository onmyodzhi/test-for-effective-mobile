package com.EffectiveMobile.test.exceptions.responses.task;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskErrorResponse {
    String message;
    Long id;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TaskErrorResponse [message=");
        builder.append(message);

        if (id != null) {
            builder.append(", id=");
            builder.append(id);
        }

        builder.append("]");
        return builder.toString();
    }
}
