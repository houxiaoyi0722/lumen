package com.sang.flowable.param;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuspendedActiveParam {
    /**
     * 流程定义id
     */
    @NotBlank(message = "流程定义id不能为空")
    private String processDefinitionId;
    /**
     * 挂起状态
     */
    @NotNull(message = "挂起状态不能为空")
    private Integer suspensionState;
}
