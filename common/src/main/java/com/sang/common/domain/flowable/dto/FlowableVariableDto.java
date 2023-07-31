package com.sang.common.domain.flowable.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class FlowableVariableDto {

    /**
     * 业务id
     */
    private String businessKey;
    /**
     * 状态
     */
    private String businessStatus;
    /**
     * 流程发起人
     */
    protected String startUserId;
    /**
     * 开始人名称
     */
    protected String startUserName;
    /**
     * 流程开始时间
     */
    protected Date startTime;
    /**
     * 流程定义id
     */
    private String processDefinitionId;
    /**
     * 流程名称
     */
    private String processDefinitionName;
    /**
     * 流程key
     */
    private String processKey;
    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * task id
     */
    private String taskId;
    /**
     * task定义id
     */
    private String taskDefinitionKey;
    /**
     * 执行id
     */
    private String executionId;
    /**
     * 操作注释
     */
    private String comment;

    /**
     * 流程处理地址
     */
    private String processDisposePath;

    /**
     * 动作
     */
    private String action;
    private String actionReason;

    private Date processEndTime;
    private Date processStartTime;

}
