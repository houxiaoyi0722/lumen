package com.sang.flowable.dto;

import com.sang.common.domain.flowable.dto.FlowableVariableDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author xiaoy
 * @date 2023/7/20 15:10
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricProcessInstanceDto extends FlowableVariableDto {

    private String endActivityId;
    private String startUserId;
    private String startActivityId;
    private String superProcessInstanceId;
    private String tenantId;
    private String name;
    private String localizedName;
    private String description;
    private String localizedDescription;
    private String processDefinitionKey;
    private String processDefinitionName;
    private Integer processDefinitionVersion;
    private String deploymentId;
    private String callbackId;
    private String callbackType;
    private String referenceId;
    private String referenceType;
    private String propagatedStageInstanceId;
}
