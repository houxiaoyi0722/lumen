package com.sang.flowable.service.flowable;

import com.sang.flowable.dto.*;
import com.sang.common.response.PageResult;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;

import java.util.List;

public interface FlowableService {

    PageResult<ProcessDefinitionDto> getProcessDefinitionDtoPageResult(String name, String startBy, Boolean active, Boolean latestVersion, Integer pageNumber, Integer pageSize);

    PageResult<FlowableTaskInfoDto> getFlowableTaskInfoDtoPageResult(String userId, String processDefineId, Integer pageNumber, Integer pageSize);

    PageResult<HistoricProcessInstanceDto> getHistoricProcessInstancePageResult(String userId, Boolean finished, Integer pageNumber, Integer pageSize);

    PageResult<HistoricTaskInstanceDto> getHistoricTaskInstancePageResult(String userId, Integer pageNumber, Integer pageSize);

    /**
     * 查询流程履历
     * @param processInstanceId
     * @return
     */
    List<HistoricActivityInstanceDto> getProcessHistory(String processInstanceId, String processDefinitionId);
}
