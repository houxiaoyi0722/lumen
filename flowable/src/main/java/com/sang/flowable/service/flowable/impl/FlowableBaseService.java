package com.sang.flowable.service.flowable.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.sang.common.constants.FlowableStatusEnum;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.flowable.dto.FlowableTaskVariableDto;
import com.sang.common.domain.flowable.dto.FlowableVariableDto;
import com.sang.common.domain.leaveProcess.dto.LeaveProcessDto;
import com.sang.flowable.service.flowable.FlowableBaseInterface;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.*;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

public abstract class FlowableBaseService<T extends BaseModel> implements FlowableBaseInterface<T> {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private HistoryService historyService;

    /**
     * 通过key启动流程
     * @param variables 流程变量
     * @param key 流程key
     * @return
     */
    public ProcessInstance startProcessByKey(T variables, String key) {
//        Authentication.setAuthenticatedUserId();
        // 启动流程实例，第一个参数是流程定义的id
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey(key, variables.getId().toString(), BeanUtil.beanToMap(variables));

        runtimeService.updateBusinessStatus(processInstance.getProcessInstanceId(), FlowableStatusEnum.APPROVAL.getCode());

        return processInstance;// 启动流程实例
    }


    /**
     * 通过id启动流程
     * @param variables 流程变量
     * @param processDefinitionId 流程定义id
     * @return
     */
    public ProcessInstance startProcessById(T variables, String processDefinitionId) {
        // 填充发起节点处理人
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> param = BeanUtil.beanToMap(variables);
        param.put("startUserId",authentication.getPrincipal().toString());

        // 启动流程实例，第一个参数是流程定义的id
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById(processDefinitionId, variables.getId().toString(), param);
        // 更新状态
        runtimeService.updateBusinessStatus(processInstance.getProcessInstanceId(), FlowableStatusEnum.APPROVAL.getCode());

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();

        // 完成发起节点
        taskService.complete(task.getId(),BeanUtil.beanToMap(FlowableTaskVariableDto.builder().action("发起申请").build()),true);
        return processInstance;
    }

    /**
     * 完成任务
     * @param variables 任务变量
     * @param taskId 任务id
     */
    public void completeTask(FlowableTaskVariableDto variables, String taskId) {
        // 完成任务
        taskService.complete(taskId,BeanUtil.beanToMap(variables),true);
    }

    /**
     * 移动激活节点(做跳过节点或者回退操作)
     * @param processInstanceId 流程实例id
     * @param newActivityId 目标节点id
     * @param currentActivityIds 待移动节点列表
     */
    public void moveActivityById(FlowableTaskVariableDto variables,String processInstanceId, String newActivityId, String... currentActivityIds) {
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(processInstanceId)
                .moveActivityIdsToSingleActivityId(Arrays.asList(currentActivityIds),newActivityId)
                .localVariables(newActivityId,BeanUtil.beanToMap(variables))
                .changeState();
    }

    /**
     * 移动当前流程激活节点(做跳过节点或者回退操作)
     * @param processInstanceId 流程实例id
     * @param executionId 当前执行id
     * @param newActivityId 目标节点id
     */
    public void moveActivityByExecution(FlowableTaskVariableDto variables, String processInstanceId, String executionId, String newActivityId) {
        // 回退操作
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(processInstanceId)
                .moveExecutionToActivityId(executionId,newActivityId)
                .localVariables(newActivityId,BeanUtil.beanToMap(variables))
                .changeState();
    }

    /**
     * 移动当前流程激活节点(做跳过节点或者回退操作)
     * @param processInstanceId 流程实例id
     * @param currentActivityId 当前节点id
     * @param newActivityId 目标节点id
     */
    public void moveActivityIdTo(FlowableTaskVariableDto variables, String processInstanceId, String currentActivityId, String newActivityId) {
        // 回退操作
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(processInstanceId)
                .moveActivityIdTo(currentActivityId,newActivityId)
                .localVariables(currentActivityId,BeanUtil.beanToMap(variables))
                .changeState();
    }


    @Override
    public void deleteProcessInstance(String processInstanceId,String deleteReason) {
        runtimeService.deleteProcessInstance(processInstanceId,deleteReason);
    }

    protected FlowableStatusEnum nextStatusByAction(String action,String... currentStatus) {

        // 保存草稿
        if (FlowableStatusEnum.ACTION_DRAFT.getCode().equals(action))
            return FlowableStatusEnum.DRAFT;
        // 发起申请 || 审批通过 - 审批中
        if (FlowableStatusEnum.ACTION_LAUNCH.getCode().equals(action) || FlowableStatusEnum.ACTION_APPROVED.getCode().equals(action))
            return FlowableStatusEnum.APPROVAL;
        // 审批驳回 - 驳回
        if (FlowableStatusEnum.ACTION_REJECT.getCode().equals(action))
            return FlowableStatusEnum.REJECT;
        // 审批退回 - 审批退回
        if (FlowableStatusEnum.ACTION_RETREAT.getCode().equals(action))
            return FlowableStatusEnum.RETREAT;

        throw new FlowableException("该动作不存在");
    }

    /**
     * 获取回退节点 - 单条线
     * @param variableDto
     * @return
     */
    protected String findNodeByAction(FlowableVariableDto variableDto) {
        String newActivityId = "";
        BpmnModel bpmnModel = repositoryService.getBpmnModel(variableDto.getProcessDefinitionId());

        // 查询历史节点实例
        List<String> activityIdList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(variableDto.getProcessInstanceId())
                .finished()
                .orderByHistoricActivityInstanceEndTime().asc().list()
                .stream()
                .filter(activityInstance ->
                        BpmnXMLConstants.ELEMENT_TASK_USER.equals(activityInstance.getActivityType()))
                .map(HistoricActivityInstance::getActivityId)
                .distinct()
                .collect(Collectors.toList());

        if (FlowableStatusEnum.ACTION_RETREAT.getCode().equals(variableDto.getAction())) {
            // 退回获取上一个节点
            newActivityId = findLastUserNode(variableDto.getTaskDefinitionKey(),bpmnModel,activityIdList);

        } else if (FlowableStatusEnum.ACTION_REJECT.getCode().equals(variableDto.getAction())) {
            // 退回第一个节点 流程中第一个处理的用户节点
            newActivityId = CollUtil.getFirst(activityIdList);
        }
        return newActivityId;
    }


    /**
     * 获取回退节点列表 - 多条线并行
     * @param variableDto
     * @return
     */
    protected List<String> findNodeListByAction(FlowableVariableDto variableDto) {
        List<String> newActivityIds = null;
        BpmnModel bpmnModel = repositoryService.getBpmnModel(variableDto.getProcessDefinitionId());

        // 查询历史节点实例
        List<String> activityIdList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(variableDto.getProcessInstanceId())
                .finished()
                .orderByHistoricActivityInstanceEndTime().asc().list()
                .stream()
                .filter(activityInstance ->
                        BpmnXMLConstants.ELEMENT_TASK_USER.equals(activityInstance.getActivityType()))
                .map(HistoricActivityInstance::getActivityId)
                .distinct()
                .collect(Collectors.toList());

        if (FlowableStatusEnum.ACTION_RETREAT.getCode().equals(variableDto.getAction())) {
            // 退回获取上一个节点
            newActivityIds = findLastUserNodeList(variableDto.getTaskDefinitionKey(),bpmnModel,activityIdList);

        } else if (FlowableStatusEnum.ACTION_REJECT.getCode().equals(variableDto.getAction())) {
            // 退回第一个节点 流程中第一个处理的用户节点
            newActivityIds = Collections.singletonList(CollUtil.getFirst(activityIdList));
        }
        return newActivityIds;
    }

    /**
     * 获取当前节点前的最后一个用户节点,适用于非并行流
     * @param taskDefinitionKey
     * @param bpmnModel
     * @return
     */
    private String findLastUserNode(String taskDefinitionKey, BpmnModel bpmnModel, List<String> activityIdList) {
        // 获取当前节点
        FlowNode flowElement = (FlowNode)bpmnModel.getFlowElement(taskDefinitionKey);
        // 获取节点来源
        List<SequenceFlow> incomingFlows = flowElement.getIncomingFlows();
        // 当前节点无前置节点则返回空
        if (CollUtil.isNotEmpty(incomingFlows)) {
            // 图上有多个来源的情况，找出最后走过的节点 --单条线执行 该方法不考虑多条线并行执行的情况
            String last = CollUtil.getLast(activityIdList);
            List<String> sourceRefs = incomingFlows.stream().map(SequenceFlow::getSourceRef).filter(last::equals).collect(Collectors.toList());
            String sourceRef = sourceRefs.get(0);

            // 如果上一个节点是用户节点并且流程走过该节点，获取id
            FlowNode task = (FlowNode)bpmnModel.getFlowElement(sourceRef);
            if (task instanceof UserTask) {
                return task.getId(); //如果当前节点是用户任务节点则返回
            } else {
                return findLastUserNode(task.getId(),bpmnModel,activityIdList); //否则以当前节点向前递归查询
            }
        }
        return null;
    }

    /**
     * 获取当前节点前的用户节点列表 适用于并行流等
     * @param taskDefinitionKey
     * @param bpmnModel
     * @return
     */
    private List<String> findLastUserNodeList(String taskDefinitionKey, BpmnModel bpmnModel, List<String> activityIdList) {

        List<String> taskIds = new ArrayList<>();
        // 获取当前节点
        FlowNode flowElement = (FlowNode)bpmnModel.getFlowElement(taskDefinitionKey);
        // 获取节点来源
        List<SequenceFlow> incomingFlows = flowElement.getIncomingFlows();
        // 当前节点无前置节点则返回空
        if (CollUtil.isNotEmpty(incomingFlows)) {

            List<String> sourceRefs = incomingFlows.stream().map(SequenceFlow::getSourceRef).filter(activityIdList::contains).collect(Collectors.toList());

            for (String sourceRef : sourceRefs) {
                // 如果上一个节点是处理节点，获取id 非处理节点再向上找
                FlowNode task = (FlowNode)bpmnModel.getFlowElement(sourceRef);
                if (task instanceof UserTask) {
                    taskIds.add(task.getId()); //如果当前节点是用户任务节点则返回
                } else {
                    taskIds.addAll(findLastUserNodeList(task.getId(),bpmnModel,activityIdList)); //否则再次调用
                }
            }
        }
        return taskIds;
    }

}
