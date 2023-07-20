package com.sang.flowable.dto;

import com.sang.common.domain.flowable.dto.FlowableVariableDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlowableTaskInfoDto extends FlowableVariableDto {
    /**
     * taskId
     */
    private String id;

    private Boolean isBatchApproval;

    private String taskDisposePath;

    private String owner;

    private int assigneeUpdatedCount;

    private String originalAssignee;

    private String assignee;

    private String parentTaskId;

    private String name;

    private String localizedName;

    private String description;

    private String localizedDescription;

    private int priority;

    private Date createTime;

    private Date dueDate;

    private int suspensionState;

    private String category;

    private boolean isIdentityLinksInitialized;

    private String executionId;

    private String processInstanceId;

    private String processDefinitionId;

    private String taskDefinitionId;

    private String scopeId;

    private String subScopeId;

    private String scopeType;

    private String scopeDefinitionId;

    private String propagatedStageInstanceId;

    private String taskDefinitionKey;

    private String formKey;

    private boolean isCanceled;

    private boolean isCountEnabled;

    private int variableCount;

    private int identityLinkCount;

    private int subTaskCount;

    private Date claimTime;

    private String tenantId;

    // Non-persisted
    private String eventName;

    private String eventHandlerId;

    private boolean forcedUpdate;

}
