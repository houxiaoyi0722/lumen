package com.sang.service.base;

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
    T startBusinessProcessing(T param);

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
    void completeTask(T variables, String taskId);

    /**
     * 完成任务时业务逻辑处理
     *
     * @return
     */
    default T completeTaskBusinessProcessing(T param) {
        return param;
    }

    /**
     * 移动激活节点(做跳过节点或者回退操作)
     * @param processInstanceId 流程实例id
     * @param newActivityId 目标节点id
     * @param currentActivityIds 待移动节点列表
     */
    void moveActivityById(T variables, String processInstanceId, String newActivityId, String... currentActivityIds);

    /**
     * 移动激活节点时业务逻辑处理
     *
     * @return
     */
    default T moveActivityBusinessProcessing(T param) {
        return param;
    }

    /**
     * 移动当前流程激活节点(做跳过节点或者回退操作)
     * @param processInstanceId 流程实例id
     * @param executionId 当前执行id
     * @param activityId 目标节点id
     */
    void moveActivityByExecution(T variables, String processInstanceId, String executionId, String activityId);

}
