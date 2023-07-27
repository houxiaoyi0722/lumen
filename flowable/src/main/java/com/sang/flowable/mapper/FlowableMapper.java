package com.sang.flowable.mapper;

import com.sang.flowable.dto.FlowableTaskInfoDto;
import com.sang.flowable.dto.HistoricProcessInstanceDto;
import com.sang.flowable.dto.HistoricTaskInstanceDto;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.persistence.entity.HistoricProcessInstanceEntityImpl;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
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

    HistoricTaskInstanceDto historicTaskInstanceToDto(HistoricTaskInstance historicTaskInstance);

    HistoricProcessInstanceDto historicProcessInstanceToDto(HistoricProcessInstanceEntityImpl historicProcessInstance);

}
