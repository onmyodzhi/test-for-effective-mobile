package com.EffectiveMobile.test.domain.task.mappers;

import com.EffectiveMobile.test.domain.task.dtos.TaskPriorityDTO;
import com.EffectiveMobile.test.domain.task.entities.TaskPriority;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskPriorityMapper {
    TaskPriority toEntity(TaskPriorityDTO taskPriorityDTO);

    TaskPriorityDTO toDto(TaskPriority taskPriority);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TaskPriority partialUpdate(TaskPriorityDTO taskPriorityDTO, @MappingTarget TaskPriority taskPriority);
}