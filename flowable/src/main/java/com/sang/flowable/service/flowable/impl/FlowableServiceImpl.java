package com.sang.flowable.service.flowable.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.sang.common.constants.FlowableConst;
import com.sang.common.constants.StringConst;
import com.sang.common.domain.flowable.dto.FlowableTaskInfoDto;
import com.sang.common.response.PageResult;
import com.sang.flowable.dto.ProcessDefinitionDto;
import com.sang.flowable.handler.FlowableExtendParamHandler;
import com.sang.flowable.service.flowable.FlowableService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ExtensionAttribute;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 工作流处理相关
 */
@Slf4j
@Service
public class FlowableServiceImpl implements FlowableService {

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private IdentityService identityService;

    @Resource
    private FlowableExtendParamHandler flowableExtendParamHandler;


    /**
     * 获取流程定义列表
     * @param name
     * @param startBy
     * @param active
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Cacheable(value = "processExtend",key = "#name + #startBy + #active + #pageNumber + #pageSize")
    @Override
    public PageResult<ProcessDefinitionDto> getProcessDefinitionDtoPageResult(String name, String startBy, Boolean active, Integer pageNumber, Integer pageSize) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        if (StrUtil.isNotBlank(name))
            processDefinitionQuery.processDefinitionNameLike(StringConst.PERCENT_SIGN.concat(name).concat(StringConst.PERCENT_SIGN));

        if (StrUtil.isNotBlank(startBy)) {
            List<Group> list = identityService.createGroupQuery().groupMember(startBy).list();
            processDefinitionQuery.startableByUserOrGroups(startBy,list.stream().map(Group::getId).collect(Collectors.toList()));
        }

        if(active != null) {
            processDefinitionQuery = active ? processDefinitionQuery.active() : processDefinitionQuery.suspended();
        }

        List<ProcessDefinitionDto> processDefinitions = processDefinitionQuery
                .latestVersion()
                .listPage(pageNumber - 1, pageSize).stream()
                .map(item -> BeanUtil.copyProperties(item, ProcessDefinitionDto.class))
                .peek(item -> item.setProcessDisposePath(flowableExtendParamHandler.getProcessExtendParam(item.getId(),FlowableConst.PROCESS_DISPOSE_PATH))) // 设置流程处理页面路径
                .collect(Collectors.toList());

        long count = processDefinitionQuery.count();

        return PageResult.ok(processDefinitions, pageNumber, pageSize, (int) count);
    }


    /**
     * 设置task相关扩展字段
     * @param item
     */
    private void setTaskExtendField(FlowableTaskInfoDto item) {

        // 流程定义相关字段
        item.setProcessDisposePath(flowableExtendParamHandler.getProcessExtendParam(item.getProcessDefinitionId(),FlowableConst.PROCESS_DISPOSE_PATH));

        // task定义相关字段
        String isBatchApproval = flowableExtendParamHandler.getTaskExtendParam(item.getProcessDefinitionId(), item.getTaskDefinitionKey(), FlowableConst.IS_BATCH_APPROVAL);
        if (StrUtil.isNotBlank(isBatchApproval))
            item.setIsBatchApproval(Boolean.valueOf(isBatchApproval));

        item.setTaskDisposePath(flowableExtendParamHandler.getTaskExtendParam(item.getProcessDefinitionId(),item.getTaskDefinitionKey(),FlowableConst.TASK_DISPOSE_PATH));

        // 流程实例相关字段
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processInstanceId(item.getProcessInstanceId()).list();
        item.setBusinessId(list.get(0).getBusinessKey());
        item.setBusinessStatus(list.get(0).getBusinessStatus());

        item.setStartUserId(list.get(0).getStartUserId());
        item.setStartTime(list.get(0).getStartTime());
        item.setProcessName(list.get(0).getProcessDefinitionName());

        List<User> userList = identityService.createUserQuery().userId(list.get(0).getStartUserId()).list();
        if (CollUtil.isNotEmpty(userList))
            item.setStartUserName(userList.get(0).getDisplayName());
    }


    @Override
    public PageResult<FlowableTaskInfoDto> getFlowableTaskInfoDtoPageResult(String userId, String processDefineId, Integer pageNumber, Integer pageSize) {
        TaskQuery taskQuery = taskService.createTaskQuery();

        if (StrUtil.isNotBlank(processDefineId))
            taskQuery.processDefinitionId(processDefineId);

        List<Task> tasks = taskQuery
                .taskCandidateOrAssigned(userId)
                .orderByTaskCreateTime().asc().listPage(pageNumber - 1, pageSize);

        List<FlowableTaskInfoDto> taskDtos = tasks.stream()
                .map(item -> BeanUtil.copyProperties(item, FlowableTaskInfoDto.class))
                .peek(this::setTaskExtendField).collect(Collectors.toList());


        long count = taskQuery.count();

        return PageResult.ok(taskDtos, pageNumber, pageSize, (int) count);
    }


    @Override
    public PageResult<HistoricProcessInstance> getHistoricProcessInstancePageResult(String userId, Boolean finished, Integer pageNumber, Integer pageSize) {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();

        //流程是否结束
        historicProcessInstanceQuery = finished ? historicProcessInstanceQuery.finished() : historicProcessInstanceQuery.unfinished();

        List<HistoricProcessInstance> historicProcessInstances = historicProcessInstanceQuery
                .startedBy(userId)
                .listPage(pageNumber - 1, pageSize);

        long count = historicProcessInstanceQuery.count();
        return PageResult.ok(historicProcessInstances, pageNumber, pageSize, (int) count);
    }

    @Override
    public PageResult<HistoricTaskInstance> getHistoricTaskInstancePageResult(String userId, Integer pageNumber, Integer pageSize) {
        // 查询已经结束的task任务,切由userid处理
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        List<HistoricTaskInstance> historicTaskInstances = historicTaskInstanceQuery.taskAssignee(userId).finished().listPage(pageNumber - 1, pageSize);

        long count = historicTaskInstanceQuery.count();
        return PageResult.ok(historicTaskInstances, pageNumber, pageSize, (int) count);
    }

}
