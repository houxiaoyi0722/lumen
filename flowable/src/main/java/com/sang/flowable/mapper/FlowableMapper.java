package com.sang.flowable.mapper;

import com.sang.common.domain.flowable.dto.FlowableTaskInfoDto;
import org.flowable.task.api.Task;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct转换器
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface FlowableMapper {

    FlowableMapper mapper = Mappers.getMapper(FlowableMapper.class);

    FlowableTaskInfoDto taskToDto(Task task);

}
