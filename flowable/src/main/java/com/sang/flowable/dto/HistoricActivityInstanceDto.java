
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
public class HistoricActivityInstanceDto extends FlowableVariableDto {
    private String activityId;
    private String activityName;
    private String activityType;
    private String assignee;
    private String deleteReason;
    private Boolean deleted;
    private Long durationInMillis;
    private Date endTime;
    private String executionId;
    private String id;
    private String idPrefix;
    private Boolean inserted;
    private String processDefinitionId;
    private String processInstanceId;
    private Long revision;
    private Long revisionNext;
    private Date startTime;
    private String taskId;
    private String tenantId;
    private Date time;
    private Long transactionOrder;
    private Boolean updated;

}
