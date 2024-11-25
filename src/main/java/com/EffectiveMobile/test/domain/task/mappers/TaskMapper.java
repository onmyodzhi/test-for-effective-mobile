package com.EffectiveMobile.test.domain.task.mappers;

import com.EffectiveMobile.test.domain.task.dtos.TaskDTO;
import com.EffectiveMobile.test.domain.task.entities.Task;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {TaskStatusMapper.class, TaskPriorityMapper.class})
public interface TaskMapper {
    Task toEntity(TaskDTO taskDTO);

    TaskDTO toDto(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task partialUpdate(TaskDTO taskDTO, @MappingTarget Task task);

}