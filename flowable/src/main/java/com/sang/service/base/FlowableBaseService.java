package com.sang.service.base;

import cn.hutool.core.bean.BeanUtil;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

public abstract class FlowableBaseService<T> {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;


    /**
     * 通过key启动流程
     * @param variables 流程变量
     * @param key 流程key
     * @param businessKey 业务主键
     * @return
     */
    public ProcessInstance startProcessByKey(T variables, String key, String businessKey) {
        // 启动流程实例，第一个参数是流程定义的id
        return runtimeService
                .startProcessInstanceByKey(key, businessKey, BeanUtil.copyProperties(variables, Map.class));// 启动流程实例
    }


    /**
     * 通过id启动流程
     * @param variables 流程变量
     * @param processDefinitionId 流程定义id
     * @return
     */
    public ProcessInstance startProcessById(T variables, String processDefinitionId, String businessKey) {
        // 启动流程实例，第一个参数是流程定义的id
        return runtimeService
                .startProcessInstanceById(processDefinitionId, businessKey, BeanUtil.copyProperties(variables, Map.class));// 启动流程实例
    }

    /**
     * 完成任务
     * @param taskId 任务id
     * @param variables 流程变量
     */
    public void completeTask(String taskId,T variables) {
        // 完成任务
        taskService.complete(taskId,BeanUtil.copyProperties(variables, Map.class));
    }


    /**
     * 移动激活节点(做跳过节点或者回退操作)
     * @param processInstanceId 流程实例id
     * @param newActivityId 目标节点id
     * @param currentActivityIds 待移动节点列表
     */
    public void moveActivityById(String processInstanceId, String newActivityId, String... currentActivityIds) {
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(processInstanceId)
                .moveActivityIdsToSingleActivityId(Arrays.asList(currentActivityIds),newActivityId)
                .changeState();
    }

    /**
     * 移动当前流程激活节点(做跳过节点或者回退操作)
     * @param processInstanceId 流程实例id
     * @param executionId 当前执行id
     * @param activityId 目标节点id
     */
    public void moveActivityByExecution(String processInstanceId, String executionId, String activityId) {
        // 回退操作
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(processInstanceId)
                .moveExecutionToActivityId(executionId,activityId)
                .changeState();
    }

}
