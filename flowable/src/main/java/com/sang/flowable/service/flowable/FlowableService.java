package com.sang.flowable.service.flowable;

import com.sang.common.domain.flowable.dto.FlowableTaskInfoDto;
import com.sang.common.response.PageResult;
import com.sang.flowable.dto.ProcessDefinitionDto;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;

public interface FlowableService {

    PageResult<ProcessDefinitionDto> getProcessDefinitionDtoPageResult(String name, String startBy, Boolean active, Boolean latestVersion, Integer pageNumber, Integer pageSize);

    PageResult<FlowableTaskInfoDto> getFlowableTaskInfoDtoPageResult(String userId, String processDefineId, Integer pageNumber, Integer pageSize);

    PageResult<HistoricProcessInstance> getHistoricProcessInstancePageResult(String userId, Boolean finished, Integer pageNumber, Integer pageSize);

    PageResult<HistoricTaskInstance> getHistoricTaskInstancePageResult(String userId, Integer pageNumber, Integer pageSize);
}
