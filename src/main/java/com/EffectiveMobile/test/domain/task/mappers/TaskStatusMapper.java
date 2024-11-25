package com.EffectiveMobile.test.domain.task.mappers;

import com.EffectiveMobile.test.domain.task.dtos.TaskStatusDTO;
import com.EffectiveMobile.test.domain.task.entities.TaskStatus;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskStatusMapper {
    TaskStatus toEntity(TaskStatusDTO taskStatusDTO);

    TaskStatusDTO toDto(TaskStatus taskStatus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TaskStatus partialUpdate(TaskStatusDTO taskStatusDTO, @MappingTarget TaskStatus taskStatus);
}