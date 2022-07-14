package com.sang.common.domain.job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TriggerVo {

    /**
     * 触发器名称
     */
    private String triggerName;
    /**
     * 触发器组
     */
    private String triggerGroup;
    /**
     * 描述
     */
    private String triggerDescription;
    /**
     * cron
     */
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
