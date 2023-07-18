package com.sang.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 工作流状态
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum FlowableStatusEnum {

    DRAFT("草稿","DRAFT")
    ,PENDING("待处理","PENDING")
    ,APPROVAL("审批中","APPROVAL")
    ,RETREAT("退回","RETREAT")
    ,REJECT("否决","REJECT")
    ,APPROVED("审批通过","APPROVED")
    ;

    private String name;
    private String code;

}
