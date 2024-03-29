package com.sang.flowable.service.flowable;

import com.sang.common.domain.flowable.dto.FlowableTaskVariableDto;
import org.flowable.engine.runtime.ProcessInstance;

/**
 * 工作流流转方法定义
 * @param <T>
 */
public interface FlowableBaseInterface<T> {

    /**
     * 启动时业务逻辑处理
     *
     * @return
     */
    default T startBusinessProcessing(T param) { return param;}

    /**
     * 通过key启动流程
     *
     * @param variables   流程变量
     * @param key         流程key
     * @return
     */
    ProcessInstance startProcessByKey(T variables, String key);

    /**
     * 通过id启动流程
     *
     * @param variables           流程变量
     * @param processDefinitionId 流程定义id
     * @return
     */
    ProcessInstance startProcessById(T variables, String processDefinitionId);

    /**
     * 完成任务
     *
     * @param variables 流程变量
     * @param taskId    任务id
     */
    void completeTask(FlowableTaskVariableDto variables, String taskId);

    /**
     * 移动激活节点(做跳过节点或者回退操作)
     * @param processInstanceId 流程实例id
     * @param newActivityId 目标节点id
     * @param currentActivityIds 待移动节点列表
     */
    void moveActivityById(FlowableTaskVariableDto variables, String processInstanceId, String newActivityId, String... currentActivityIds);

    /**
     * 移动当前流程激活节点(做跳过节点或者回退操作)
     * @param processInstanceId 流程实例id
     * @param executionId 当前执行id
     * @param activityId 目标节点id
     */
    void moveActivityByExecution(FlowableTaskVariableDto variables, String processInstanceId, String executionId, String activityId);

    /**
     * 移动当前流程激活节点(做跳过节点或者回退操作)
     * @param processInstanceId 流程实例id
     * @param currentActivityId 当前节点id
     * @param newActivityId 目标节点id
     */
    void moveActivityIdTo(FlowableTaskVariableDto variables, String processInstanceId, String currentActivityId, String newActivityId);

    /**
     * 删除流程实例
     * @param processInstanceId 流程实例id
     * @param processInstanceId 删除原因
     */
    void deleteProcessInstance(String processInstanceId,String deleteReason);

}
