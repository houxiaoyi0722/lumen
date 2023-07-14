package com.sang.common.domain.flowable.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class FlowableVariableDto {

    /**
     * 流程定义id
     */
    private String processDefinitionId;
    /**
     * 流程key
     */
    private String processKey;
    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 任务id
     */
    private String taskId;
    /**
     * 操作注释
     */
    private String comment;

}
