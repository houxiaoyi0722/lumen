package com.sang.common.domain.flowable.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class FlowableTaskVariableDto {

    /**
     * 执行动作
     */
    private String action;

    /**
     * 动作原因
     */
    private String actionReason;

}
