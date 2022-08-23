package com.sang.common.domain.job;

import com.sang.common.validate.job.TriggerSave;
import com.sang.common.validate.job.TriggerDelete;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TriggerVo {

    /**
     * 触发器名称
     */
    @NotBlank(message = "triggerName不能为空",groups = {TriggerSave.class, TriggerDelete.class})
    private String triggerName;
    /**
     * 触发器组
     */
    @NotBlank(message = "triggerGroup不能为空",groups = {TriggerSave.class, TriggerDelete.class})
    private String triggerGroup;
    /**
     * 描述
     */
    private String triggerDescription;
    /**
     * cron
     */
    @NotBlank(message = "cronExpression不能为空",groups = TriggerSave.class)
    private String cronExpression;


    /**
     * 状态
     */
    private String status;
    /**
     * 触发器是否会再次触发
     */
    private Boolean mayFireAgain;
    /**
     * 触发器最后一次Trigger的时间
     */
    private Date finalFireTime;
    /**
     * Trigger计划触发的下一次时间。如果触发器不会再次触发，则返回null
     */
    private Date nextFireTime;
    /**
     * 计划的上次触发时间
     */
    private Date previousFireTime;
}
