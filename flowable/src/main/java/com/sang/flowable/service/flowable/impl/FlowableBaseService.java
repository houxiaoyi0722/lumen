package com.sang.flowable.service.flowable.impl;

import cn.hutool.core.bean.BeanUtil;
import com.sang.common.constants.FlowableStatusEnum;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.flowable.service.flowable.FlowableBaseInterface;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;

import javax.annotation.Resource;
import java.util.Arrays;

public abstract class FlowableBaseService<T extends BaseModel> implements FlowableBaseInterface<T> {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

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
        // 启动流程实例，第一个参数是流程定义的id
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById(processDefinitionId, variables.getId().toString(), BeanUtil.beanToMap(variables));

        runtimeService.updateBusinessStatus(processInstance.getProcessInstanceId(), FlowableStatusEnum.APPROVAL.getCode());

        return processInstance;// 启动流程实例
    }

    /**
     * 完成任务
     * @param variables 流程变量
     * @param taskId 任务id
     */
    public void completeTask(T variables,String taskId) {
        // 完成任务
        taskService.complete(taskId,BeanUtil.beanToMap(variables));
    }

    /**
     * 移动激活节点(做跳过节点或者回退操作)
     * @param processInstanceId 流程实例id
     * @param newActivityId 目标节点id
     * @param currentActivityIds 待移动节点列表
     */
    public void moveActivityById(T variables,String processInstanceId, String newActivityId, String... currentActivityIds) {
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(processInstanceId)
                .moveActivityIdsToSingleActivityId(Arrays.asList(currentActivityIds),newActivityId)
                .processVariables(BeanUtil.beanToMap(variables))
                .changeState();
    }

    /**
     * 移动当前流程激活节点(做跳过节点或者回退操作)
     * @param processInstanceId 流程实例id
     * @param executionId 当前执行id
     * @param activityId 目标节点id
     */
    public void moveActivityByExecution(T variables, String processInstanceId, String executionId, String activityId) {
        // 回退操作
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(processInstanceId)
                .moveExecutionToActivityId(executionId,activityId)
                .changeState();
    }


    @Override
    public void deleteProcessInstance(T variables, String processInstanceId,String deleteReason) {
        runtimeService.deleteProcessInstance(processInstanceId,deleteReason);
    }

    public FlowableStatusEnum nextStatusByAction(String action,String... currentStatus) {

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
}
