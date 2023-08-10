package com.sang.flowable.service.flowable.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.sang.common.constants.FlowableConst;
import com.sang.common.constants.StringConst;
import com.sang.common.domain.auth.authentication.user.entity.User;
import com.sang.common.domain.auth.authentication.user.repo.UserRepository;
import com.sang.common.domain.flowable.dto.FlowableVariableDto;
import com.sang.flowable.dto.*;
import com.sang.common.response.PageResult;
import com.sang.flowable.handler.FlowableExtendParamHandler;
import com.sang.flowable.mapper.FlowableMapper;
import com.sang.flowable.service.flowable.FlowableService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.impl.persistence.entity.HistoricActivityInstanceEntityImpl;
import org.flowable.engine.impl.persistence.entity.HistoricProcessInstanceEntityImpl;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.Group;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Comparator;
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
    private UserRepository userRepository;

    @Resource
    private FlowableExtendParamHandler flowableExtendParamHandler;

    private final FlowableMapper flowableMapper = FlowableMapper.mapper;



    /**
     * 获取流程定义列表
     * @param name
     * @param startBy
     * @param active
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Cacheable(value = "flowableCache",key = "'ProcessDefinition:' + #name + #startBy + #active + #latestVersion + #pageNumber + #pageSize")
    @Override
    public PageResult<ProcessDefinitionDto> getProcessDefinitionDtoPageResult(String name, String startBy, Boolean active, Boolean latestVersion, Integer pageNumber, Integer pageSize) {
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

        if(latestVersion != null && latestVersion) {
            processDefinitionQuery = processDefinitionQuery.latestVersion();
        }

        List<ProcessDefinitionDto> processDefinitions = processDefinitionQuery
                .listPage((pageNumber - 1)*pageSize, pageSize).stream()
                .map(item -> BeanUtil.copyProperties(item, ProcessDefinitionDto.class))
                .peek(item -> item.setProcessDisposePath(flowableExtendParamHandler.getProcessExtendParam(item.getId(),FlowableConst.PROCESS_DISPOSE_PATH))) // 设置流程处理页面路径
                .collect(Collectors.toList());

        long count = processDefinitionQuery.count();

        return PageResult.ok(processDefinitions, pageNumber, pageSize, (int) count);
    }


    @Override
    public PageResult<FlowableTaskInfoDto> getFlowableTaskInfoDtoPageResult(String userId, String processDefineId, Integer pageNumber, Integer pageSize) {

        TaskQuery taskQuery = taskService.createTaskQuery();

        if (StrUtil.isNotBlank(processDefineId))
            taskQuery.processDefinitionId(processDefineId);

        List<Task> tasks = taskQuery
                .taskCandidateOrAssigned(userId)
                .orderByTaskCreateTime().asc().listPage((pageNumber - 1) * pageSize, pageSize);

        List<FlowableTaskInfoDto> taskDtos = tasks.stream()
                .map(flowableMapper::taskToDto)
                .peek(this::setTaskExtendField)
                .collect(Collectors.toList());

        long count = taskQuery.count();

        return PageResult.ok(taskDtos, pageNumber, pageSize, (int) count);
    }


    @Override
    public PageResult<HistoricProcessInstanceDto> getHistoricProcessInstancePageResult(String userId, Boolean finished, Integer pageNumber, Integer pageSize) {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();

        //流程是否结束
        historicProcessInstanceQuery = finished ? historicProcessInstanceQuery.finished() : historicProcessInstanceQuery.unfinished();

        List<HistoricProcessInstanceDto> historicProcessInstances = historicProcessInstanceQuery
                .startedBy(userId)
                .listPage((pageNumber - 1)*pageSize, pageSize)
                .stream()
                .map(item -> flowableMapper.historicProcessInstanceToDto((HistoricProcessInstanceEntityImpl)item))
                .peek(this::setHistoricProcessExtendField)
                .collect(Collectors.toList());

        long count = historicProcessInstanceQuery.count();
        return PageResult.ok(historicProcessInstances, pageNumber, pageSize, (int) count);
    }

    @Override
    public PageResult<HistoricTaskInstanceDto> getHistoricTaskInstancePageResult(String userId, Integer pageNumber, Integer pageSize) {
        // 查询已经结束的task任务,且由userid处理
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        List<HistoricTaskInstanceDto> historicTaskInstances = historicTaskInstanceQuery
                .taskAssignee(userId)
                .finished()
                .listPage((pageNumber - 1)*pageSize, pageSize)
                .stream()
                .map(flowableMapper::historicTaskInstanceToDto)
                .peek(this::setHistoricExtendField)
                .collect(Collectors.toList());

        long count = historicTaskInstanceQuery.count();
        return PageResult.ok(historicTaskInstances, pageNumber, pageSize, (int) count);
    }

    @Override
    public List<HistoricActivityInstanceDto> getProcessHistory(String processInstanceId, String processDefinitionId) {

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        List<HistoricActivityInstanceDto> list = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceStartTime().asc()
                .list()
                .stream()
                .filter(item -> "userTask".equals(item.getActivityType())) // 只展示人员处理履历
                .map(item -> flowableMapper.historicActivityInstanceToDto((HistoricActivityInstanceEntityImpl)item))
                .peek(item -> {
                    if (StrUtil.isNotBlank(item.getDeleteReason())) { // 转换taskid 为名称
                        List<String> strings = Arrays.asList(item.getDeleteReason().split(StringConst.SPACE));
                        String last = CollUtil.getLast(strings);

                        FlowElement flowElement = bpmnModel.getFlowElement(last);
                        String name = flowElement == null || StrUtil.isBlank(flowElement.getName())? last : flowElement.getName();

                        strings.set(strings.size()-1, name);
                        item.setDeleteReason(String.join(StringConst.SPACE, strings));
                    }
                    Map<String, String> historicVariable = historyService.createHistoricVariableInstanceQuery()
                            .taskId(item.getTaskId())
                            .list().stream().collect(Collectors.toMap(HistoricVariableInstance::getVariableName, instance -> instance.getValue() != null? instance.getValue().toString():StringConst.EMPTY));

                    // 获取task 动作和动作原因
                    item.setAction(historicVariable.get(FlowableConst.ACTION));
                    item.setActionReason(historicVariable.get(FlowableConst.ACTION_REASON));
                })
                .sorted(Comparator.comparing(HistoricActivityInstanceDto::getStartTime))
                .collect(Collectors.toList());

        return list;
    }


    private void setHistoricExtendField(FlowableVariableDto item) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(item.getProcessInstanceId()).singleResult();
        item.setProcessDefinitionName(historicProcessInstance.getProcessDefinitionName());
        item.setBusinessStatus(historicProcessInstance.getBusinessStatus());
        item.setBusinessKey(historicProcessInstance.getBusinessKey());
        item.setStartUserId(historicProcessInstance.getStartUserId());
        item.setProcessEndTime(historicProcessInstance.getEndTime());
        item.setProcessStartTime(historicProcessInstance.getStartTime());

        User userinfo = userRepository.userinfo(historicProcessInstance.getStartUserId());
        item.setStartUserName(userinfo.getName());
    }

    private void setHistoricProcessExtendField(HistoricProcessInstanceDto item) {
        User userinfo = userRepository.userinfo(item.getStartUserId());
        item.setStartUserName(userinfo.getName());
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
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(item.getProcessInstanceId()).singleResult();
        item.setBusinessKey(processInstance.getBusinessKey());
        item.setBusinessStatus(processInstance.getBusinessStatus());
        item.setStartUserId(processInstance.getStartUserId());
        item.setStartTime(processInstance.getStartTime());
        item.setProcessDefinitionName(processInstance.getProcessDefinitionName());

        User userinfo = userRepository.userinfo(processInstance.getStartUserId());
        item.setStartUserName(userinfo.getName());
    }

}
