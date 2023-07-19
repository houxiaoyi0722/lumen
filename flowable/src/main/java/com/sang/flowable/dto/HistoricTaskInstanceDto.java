package com.sang.flowable.dto;

import com.sang.common.domain.flowable.dto.FlowableVariableDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.flowable.task.service.TaskServiceConfiguration;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricTaskInstanceDto extends FlowableVariableDto {

    private String businessStatus;
    private String businessId;
    private String executionId;
    private String processInstanceId;
    private String processDefinitionId;
    private String taskDefinitionId;
    private String scopeId;
    private String subScopeId;
    private String scopeType;
    private String scopeDefinitionId;
    private String propagatedStageInstanceId;
    private Date createTime;
    private Date endTime;
    private Long durationInMillis;
    private String deleteReason;
    private String name;
    private String localizedName;
    private String parentTaskId;
    private String description;
    private String localizedDescription;
    private String owner;
    private String assignee;
    private String taskDefinitionKey;
    private String formKey;
    private int priority;
    private Date dueDate;
    private Date claimTime;
    private String category;
    private String tenantId = TaskServiceConfiguration.NO_TENANT_ID;
    private Date lastUpdateTime;
    private boolean isIdentityLinksInitialized;

}
