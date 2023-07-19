package com.sang.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *       工作流状态流转 区分动作和状态
 *          动作:         状态:
 *          保存草稿            草稿
 *          发起申请            待处理
 *          审批通过           审批中
 *          审批驳回           审批未通过
 *          审批退回           审批退回
 *          流程结束            审批通过
 * 工作流状态
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum FlowableStatusEnum {

    DRAFT("草稿","DRAFT")
    ,PENDING("待处理","PENDING")
    ,APPROVAL("审批中","APPROVAL")
    ,RETREAT("审批退回","RETREAT")
    ,REJECT("审批驳回","REJECT")
    ,APPROVED("审批通过","APPROVED")

    ,ACTION_DRAFT("保存草稿","ACTION_DRAFT")
    ,ACTION_LAUNCH("发起申请","ACTION_LAUNCH")
    ,ACTION_APPROVAL("审批","ACTION_APPROVAL")
    ,ACTION_APPROVED("审批通过","ACTION_APPROVED")
    ,ACTION_REJECT("审批驳回","ACTION_REJECT")
    ,ACTION_RETREAT("审批退回","ACTION_RETREAT")
    ;

    private String name;
    private String code;

}
